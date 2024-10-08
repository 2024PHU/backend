package com.phu.backend.repository.member;

import com.phu.backend.domain.member.Member;
import com.phu.backend.domain.member.MemberList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface MemberListRepository extends JpaRepository<MemberList, Long> {
    Optional<MemberList> findByTrainerAndMemberEmail(Member member, String email);

    List<MemberList> findAllByTrainer(Member member);
}
