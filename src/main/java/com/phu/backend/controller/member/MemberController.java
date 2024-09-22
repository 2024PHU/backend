package com.phu.backend.controller.member;

import com.phu.backend.dto.member.request.SingInRequest;
import com.phu.backend.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "회원 서비스 API", description = "회원과 관련된 서비스를 제공하는 API")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/sign-in")
    @Operation(summary = "회원가입", description = "사용자가 회원가입을 한다")
    public void signIn(@RequestBody @Valid SingInRequest request) {
        memberService.signIn(request);
    }
}
