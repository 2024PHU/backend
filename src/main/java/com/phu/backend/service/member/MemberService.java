package com.phu.backend.service.member;

import com.phu.backend.domain.member.Member;
import com.phu.backend.domain.member.MemberList;
import com.phu.backend.domain.member.MemberMemo;
import com.phu.backend.domain.member.Part;
import com.phu.backend.dto.auth.MemberDetails;
import com.phu.backend.dto.member.request.*;
import com.phu.backend.dto.member.response.MemberInfoResponse;
import com.phu.backend.dto.member.response.MemberMemoResponse;
import com.phu.backend.dto.member.response.MemberResponse;
import com.phu.backend.exception.member.*;
import com.phu.backend.repository.member.MemberListRepository;
import com.phu.backend.repository.member.MemberMemoRepository;
import com.phu.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberListRepository memberListRepository;

    private final MemberMemoRepository memberMemoRepository;

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
    public void signUpSocial(SignUpSocial request) {
        Member member = getMember();
        member.signUpForSocial(request.getAge(), request.getGender(), request.getTel(), request.getPart(), "ROLE_USER");
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
    public Long addMember(AddMemberRequest request) {
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
                .memberName(member.getName())
                .build();

        memberListRepository.save(memberList);

        return memberList.getId();
    }

    public MemberMemoResponse getMyMemberInfo(Long id) {
        MemberList memberList = memberListRepository.findById(id).orElseThrow(NotFoundMemberException::new);

        MemberMemo memberMemo = memberMemoRepository.findByMemberEmail(memberList.getMemberEmail())
                .orElseThrow(NotFoundMemberException::new);

        Member member = memberRepository.findByEmail(memberMemo.getMemberEmail())
                .orElseThrow(NotFoundMemberException::new);

        return MemberMemoResponse.builder()
                .id(member.getId())
                .memberName(memberMemo.getMemberName())
                .memberAge(memberMemo.getMemberAge())
                .memberTarget(memberMemo.getMemberTarget())
                .significant(memberMemo.getSignificant())
                .ptStartDate(memberMemo.getPtStartDate())
                .ptEndDate(memberMemo.getPtEndDate())
                .build();
    }

    public List<MemberInfoResponse> getMyMemberInfoList() {
        Member member = getMember();

        List<MemberList> list = memberListRepository.findAllByTrainer(member);

        return list.stream().map(MemberInfoResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = getMember();

        if (member.getPart() == Part.MEMBER) {
            throw new TrainerRoleException();
        }

        memberListRepository.deleteById(id);
    }

    @Transactional
    public void updateMember(MemberUpdateRequest request) {
        Member member = getMember();
        member.update(request.getName(), request.getAge(), request.getTel());
    }

    @Transactional
    public void addMemberInfo(Long id, AddMemberMemoRequest request) {
        Member trainer = getMember();

        MemberList memberList = memberListRepository.findById(id).orElseThrow(NotFoundMemberException::new);

        String email = memberList.getMemberEmail();

        Member member = memberRepository.findByEmail(email).orElseThrow(NotFoundMemberException::new);

        MemberMemo memo = MemberMemo.builder()
                .memberAge(member.getAge())
                .memberName(member.getName())
                .memberTarget(request.getMemberTarget())
                .significant(request.getSignificant())
                .ptStartDate(request.getPtStartDate())
                .ptEndDate(request.getPtEndDate())
                .trainer(trainer)
                .memberEmail(member.getEmail())
                .build();

        memberMemoRepository.save(memo);
    }
}
