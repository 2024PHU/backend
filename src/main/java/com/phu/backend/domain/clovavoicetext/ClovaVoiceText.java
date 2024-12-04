package com.phu.backend.domain.clovavoicetext;

import com.phu.backend.dto.clova.ClovaSpeechResponse;
import com.phu.backend.global.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@RedisHash("clovaVoiceText")
public class ClovaVoiceText extends BaseTimeEntity {
    @Id
    private UUID id;
    private List<ClovaSpeechResponse> voiceList;
    private Long trainerId;
    private Long memberId;

    @Builder
    public ClovaVoiceText(UUID id, List<ClovaSpeechResponse> voiceList, Long trainerId, Long memberId) {
        this.id = id;
        this.voiceList = voiceList;
        this.trainerId = trainerId;
        this.memberId = memberId;
    }
}
