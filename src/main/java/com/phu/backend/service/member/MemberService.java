package com.phu.backend.service.member;

import com.phu.backend.domain.member.Member;
import com.phu.backend.dto.member.request.SignUpRequest;
import com.phu.backend.dto.member.request.SignUpSocial;
import com.phu.backend.exception.member.DuplicationEmailException;
import com.phu.backend.exception.oauth.NotFoundSocialIdException;
import com.phu.backend.repository.member.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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
    public void signUp(SignUpRequest request) {
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

    @Transactional
    public void signUpSocial(SignUpSocial request, HttpServletRequest servletRequest) {
        String socialId = null;
        Cookie[] cookies = servletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("social_id")) {
                socialId = cookie.getValue();
            }
        }
        if (socialId == null) {
            throw new NotFoundSocialIdException();
        }

        Member member = memberRepository.findBySocialId(socialId);
        String password = passwordEncoder.encode(request.getPassword());

        member.signUpForSocial(password, request.getAge(), request.getGender(), request.getTel(), request.getPart(), "ROLE_USER");
    }
}
