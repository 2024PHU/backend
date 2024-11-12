package com.phu.backend.service.voicefile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.phu.backend.dto.voicefile.response.VoiceFileResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class VoiceFileService {
    private final AmazonS3Client s3;
    @Value("${ncp.s3.bucket}")
    private String bucketName;

    public VoiceFileResponse uploadVoiceFile(MultipartFile multipartFile) {
        // 객체 이름을 고유하게 생성 (예: UUID 사용)
        String objectName = UUID.randomUUID().toString() + "_" + multipartFile.getOriginalFilename();

        // 임시 파일 생성
        File file = null;
        String uploadId = null;

        try {
            file = convertMultipartFileToFile(multipartFile);

            long contentLength = file.length();
            long partSize = 10 * 1024 * 1024; // 10MB

            // 멀티파트 업로드 초기화
            InitiateMultipartUploadRequest initRequest = new InitiateMultipartUploadRequest(bucketName, objectName);
            InitiateMultipartUploadResult initResponse = s3.initiateMultipartUpload(initRequest);
            uploadId = initResponse.getUploadId();

            List<PartETag> partETagList = new ArrayList<>();
            long fileOffset = 0;
            int partNumber = 1;

            while (fileOffset < contentLength) {
                partSize = Math.min(partSize, (contentLength - fileOffset));

                UploadPartRequest uploadPartRequest = new UploadPartRequest()
                        .withBucketName(bucketName)
                        .withKey(objectName)
                        .withUploadId(uploadId)
                        .withPartNumber(partNumber)
                        .withFile(file)
                        .withFileOffset(fileOffset)
                        .withPartSize(partSize);

                UploadPartResult uploadPartResult = s3.uploadPart(uploadPartRequest);
                partETagList.add(uploadPartResult.getPartETag());

                fileOffset += partSize;
                partNumber++;
            }

            CompleteMultipartUploadRequest compRequest = new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, partETagList);
            CompleteMultipartUploadResult compResult = s3.completeMultipartUpload(compRequest);

            // 객체 URL 생성
            String objectUrl = s3.getUrl(bucketName, objectName).toString();

            return VoiceFileResponse.builder()
                    .uploadFileUrl(objectUrl)
                    .originalFileName(multipartFile.getOriginalFilename())
                    .uploadFileName(objectName)
                    .message("SUCCESS")
                    .build();

        } catch (AmazonServiceException e) {
            e.printStackTrace();

            if (uploadId != null) {
                abortMultipartUpload(bucketName, objectName, uploadId);
            }
            return VoiceFileResponse.builder()
                    .message("FAIL")
                    .build();

        } catch (AmazonClientException | IOException e) {
            e.printStackTrace();
            return VoiceFileResponse.builder()
                    .message("FAIL")
                    .build();

        } finally {
            if (file != null && file.exists()) {
                file.delete();
            }
        }
    }

    // MultipartFile을 File로 변환하는 유틸리티 메서드
    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + multipartFile.getOriginalFilename());
        multipartFile.transferTo(convFile);
        return convFile;
    }
    private void abortMultipartUpload(String bucketName, String objectName, String uploadId) {
        try {
            s3.abortMultipartUpload(new AbortMultipartUploadRequest(bucketName, objectName, uploadId));
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }
    }
}
