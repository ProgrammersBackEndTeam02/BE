package com.team02.be.controller;

import com.team02.be.dto.MemberResponse;
import com.team02.be.dto.MemberSignupRequest;
import com.team02.be.member.Member;
import com.team02.be.member.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "member-controller", description = "회원 API")
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @Operation(summary = "회원가입", description = "회원을 생성합니다.")
    @PostMapping
    public MemberResponse signup(@Valid @RequestBody MemberSignupRequest request) {
        Member member = Member.builder()
                .email(request.email())
                .password(request.password())
                .name(request.name())
                .build();

        Member saved = memberRepository.save(member);

        return new MemberResponse(saved.getId(), saved.getEmail(), saved.getName());
    }

    @Operation(summary = "회원 조회", description = "ID로 회원을 조회합니다.")
    @GetMapping("/{id}")
    public MemberResponse findMember(@PathVariable Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        return new MemberResponse(member.getId(), member.getEmail(), member.getName());
    }
}