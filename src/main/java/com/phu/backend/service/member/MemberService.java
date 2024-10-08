package com.phu.backend.service.member;

import com.phu.backend.domain.member.Member;
import com.phu.backend.domain.member.MemberList;
import com.phu.backend.domain.member.Part;
import com.phu.backend.dto.auth.MemberDetails;
import com.phu.backend.dto.member.request.AddMemberRequest;
import com.phu.backend.dto.member.request.SignUpRequest;
import com.phu.backend.dto.member.request.SignUpSocial;
import com.phu.backend.dto.member.response.MemberResponse;
import com.phu.backend.exception.member.*;
import com.phu.backend.exception.oauth.NotFoundSocialIdException;
import com.phu.backend.repository.member.MemberListRepository;
import com.phu.backend.repository.member.MemberRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberListRepository memberListRepository;

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

    public MemberResponse userInfo() {
        Member member = getMember();

        return MemberResponse.builder()
                .age(member.getAge())
                .email(member.getEmail())
                .tel(member.getTel())
                .name(member.getName())
                .gender(member.getGender())
                .build();
    }

    public Member getMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        String email = memberDetails.getEmail();
        log.info("member_email:{}",email);

        Member member = memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);

        return member;
    }

    @Transactional
    public void addMember(AddMemberRequest request) {
        Member trainer = getMember();

        if (request.getEmail().equals(trainer.getEmail())) {
            throw new AddOnMeException();
        }

        if (trainer.getPart() == Part.MEMBER) {
            throw new TrainerRoleException();
        }

        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(NotFoundMemberException::new);

        if (memberListRepository.findByTrainerAndMemberEmail(trainer, member.getEmail()).isPresent()) {
            throw new ListExistException();
        }

        MemberList memberList = MemberList.builder()
                .trainer(trainer)
                .memberEmail(request.getEmail())
                .build();

        memberListRepository.save(memberList);
    }
}
