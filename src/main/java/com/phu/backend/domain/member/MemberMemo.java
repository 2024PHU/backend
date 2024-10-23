package com.phu.backend.domain.member;

import com.phu.backend.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberMemo extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_memo_id")
    private Long id;
    private String memberTarget;
    private String memberName;
    private Integer memberAge;
    private String significant;
    private LocalDate ptStartDate;
    private LocalDate ptEndDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Member trainer;
    private String memberEmail;
    @Builder
    public MemberMemo(String memberTarget, String memberName, Integer memberAge, String significant, LocalDate ptStartDate, LocalDate ptEndDate, Member trainer, String memberEmail) {
        this.memberTarget = memberTarget;
        this.memberName = memberName;
        this.memberAge = memberAge;
        this.significant = significant;
        this.ptStartDate = ptStartDate;
        this.ptEndDate = ptEndDate;
        this.trainer = trainer;
        this.memberEmail = memberEmail;
    }
}
