package com.phu.backend.domain.member;

import com.phu.backend.global.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberList extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "list_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Member trainer;
    private String memberEmail;
    private String memberName;
    @Builder
    public MemberList(Member trainer, String memberEmail, String memberName) {
        this.trainer = trainer;
        this.memberEmail = memberEmail;
        this.memberName = memberName;
    }
}
