package com.phu.backend.domain.voicefile;

import com.phu.backend.domain.member.Member;
import com.phu.backend.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VoiceFile extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voice_file_id")
    private Long id;
    private String fileName;
    private String uploadFileUrl;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member trainer;
    private Long memberId;

    @Builder
    public VoiceFile(String fileName, String uploadFileUrl, Member trainer, Long memberId) {
        this.fileName = fileName;
        this.uploadFileUrl = uploadFileUrl;
        this.trainer = trainer;
        this.memberId = memberId;
    }
}
