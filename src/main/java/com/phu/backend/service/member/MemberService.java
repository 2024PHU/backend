package com.phu.backend.service.member;

import com.phu.backend.domain.member.Member;
import com.phu.backend.dto.member.request.SingInRequest;
import com.phu.backend.exception.member.DuplicationEmailException;
import com.phu.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void signIn(SingInRequest request) {
        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new DuplicationEmailException();
        }

        Member member = Member.builder()
                .tel(request.getTel())
                .name(request.getName())
                .email(request.getEmail())
                .part(request.getPart())
                .gender(request.getGender())
                .password(passwordEncoder.encode(request.getPassword()))
                .age(request.getAge())
                .role("ROLE_USER")
                .build();

        memberRepository.save(member);
    }
}
