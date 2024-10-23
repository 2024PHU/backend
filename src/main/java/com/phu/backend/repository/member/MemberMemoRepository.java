package com.phu.backend.repository.member;

import com.phu.backend.domain.member.MemberMemo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberMemoRepository extends JpaRepository<MemberMemo, Long> {
    Optional<MemberMemo> findByMemberEmail(String email);
}
