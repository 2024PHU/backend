package com.phu.backend.controller.member;

import com.phu.backend.dto.member.request.AddMemberRequest;
import com.phu.backend.dto.member.request.SignUpRequest;
import com.phu.backend.dto.member.request.SignUpSocial;
import com.phu.backend.dto.member.response.MemberResponse;
import com.phu.backend.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 서비스 API", description = "회원과 관련된 서비스를 제공하는 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-up")
    @Operation(summary = "회원가입", description = "사용자가 기본 회원가입을 한다")
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        memberService.signUp(request);
    }

    @PostMapping("/sign-up/social")
    @Operation(summary = "소셜 회원가입", description = "사용자가 소셜 로그인 회원가입을 한다")
    public void signUp(@RequestBody @Valid SignUpSocial request, HttpServletRequest servletRequest) {
        memberService.signUpSocial(request, servletRequest);
    }

    @PostMapping("/member/add")
    @Operation(summary = "회원 추가", description = "트레이너가 자신의 회원을 추가한다.")
    public void addMember(@RequestBody @Valid AddMemberRequest request) {
        memberService.addMember(request);
    }

    @GetMapping("/member")
    @Operation(summary = "자기정보 조회", description = "사용자가 자기 정보를 조회한다")
    public ResponseEntity<MemberResponse> userInfo() {
        return ResponseEntity.ok().body(memberService.userInfo());
    }
}
