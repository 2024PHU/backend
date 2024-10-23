package com.phu.backend.controller.member;

import com.phu.backend.dto.member.request.*;
import com.phu.backend.dto.member.response.MemberInfoResponse;
import com.phu.backend.dto.member.response.MemberMemoResponse;
import com.phu.backend.dto.member.response.MemberResponse;
import com.phu.backend.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void signUp(@RequestBody @Valid SignUpSocial request) {
        memberService.signUpSocial(request);
    }

    @PostMapping("/member/add")
    @Operation(summary = "회원 추가", description = "트레이너가 자신의 회원을 추가한다.")
    public ResponseEntity<Long> addMember(@RequestBody @Valid AddMemberRequest request) {
        return ResponseEntity.ok(memberService.addMember(request));
    }

    @PostMapping("/pt/add/info/{member-list-id}")
    @Operation(summary = "회원정보 입력", description = "트레이너가 추가한 회원의 상세정보를 입력받는다.")
    public void addMemberInfo(@PathVariable(name = "member-list-id") Long id, @RequestBody @Valid AddMemberMemoRequest request) {
        memberService.addMemberInfo(id,request);
    }

    @GetMapping("/pt/info/{member-list-id}")
    @Operation(summary = "회원정보 상세조회", description = "트레이너의 회원정보를 상세조회한다.")
    public ResponseEntity<MemberMemoResponse> getMemberInfo(@PathVariable(name = "member-list-id") Long id) {
        return ResponseEntity.ok(memberService.getMyMemberInfo(id));
    }

    @GetMapping("/member")
    @Operation(summary = "자기정보 조회", description = "사용자가 자기 정보를 조회한다")
    public ResponseEntity<MemberResponse> userInfo() {
        return ResponseEntity.ok().body(memberService.userInfo());
    }

    @GetMapping("/pt/member")
    @Operation(summary = "회원 전체 조회", description = "트레이너가 자신의 PT 회원을 전체조회한다.")
    public ResponseEntity<List<MemberInfoResponse>> memberInfoList() {
        return ResponseEntity.ok(memberService.getMyMemberInfoList());
    }

    @DeleteMapping("/pt/member/{member-list-id}")
    @Operation(summary = "회원 삭제", description = "트레이너가 자신의 PT 회원을 삭제시킨다.")
    public void memberInfoList(@PathVariable(name = "member-list-id") Long id) {
        memberService.deleteMember(id);
    }

    @PutMapping("/pt/member")
    @Operation(summary = "사용자 정보 수정", description = "자신의 정보를 수정한다.")
    public void updateMyInfo(@RequestBody @Valid MemberUpdateRequest request){
        memberService.updateMember(request);
    }
}
