package com.phu.backend.dto.summarization.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
public class SummarizationResponse {
    private Long summarizationId;
    private Long trainerId;
    private Long memberId;
    private UUID voiceListId;
    private String texts;
    private LocalDateTime createAt;

    @Builder
    public SummarizationResponse(Long summarizationId, Long trainerId, Long memberId, UUID voiceListId, String texts, LocalDateTime createAt) {
        this.summarizationId = summarizationId;
        this.trainerId = trainerId;
        this.memberId = memberId;
        this.voiceListId = voiceListId;
        this.texts = texts;
        this.createAt = createAt;
    }
}
