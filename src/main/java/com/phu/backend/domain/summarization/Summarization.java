package com.phu.backend.domain.summarization;

import com.phu.backend.domain.member.Member;
import com.phu.backend.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Summarization extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "summarization_id")
    private Long id;
    @Lob
    @Column(name = "summarization_texts", columnDefinition = "LONGTEXT")
    private String summarizationTexts;
    private UUID voiceTextId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member trainer;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Builder
    public Summarization(String summarizationTexts, UUID voiceTextId, Member trainer, Member member) {
        this.summarizationTexts = summarizationTexts;
        this.voiceTextId = voiceTextId;
        this.trainer = trainer;
        this.member = member;
    }
}
