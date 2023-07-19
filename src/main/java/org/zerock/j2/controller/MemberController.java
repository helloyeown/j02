package org.zerock.j2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.service.MemberService;
import org.zerock.j2.service.SocialService;
import org.zerock.j2.util.JwtUtil;

import java.util.Map;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/member/")
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final SocialService socialService;
    private final JwtUtil jwtUtil;

    // 카카오 로그인
    @GetMapping("kakao")
    public MemberDTO getAuthCode(String code){

        log.info("-------------------------------");
        log.info(code);

        String email = socialService.getKakaoEmail(code);

        MemberDTO memberDTO = memberService.getMemberWithEmail(email);

        return memberDTO;
    }

    // 일반 로그인
    @PostMapping("login")
    public MemberDTO login(@RequestBody MemberDTO memberDTO){
        // 제이슨으로 응답
        log.info("Parameter: " + memberDTO);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        MemberDTO result = memberService.login(memberDTO.getEmail(), memberDTO.getPw());

        result.setAccessToken(jwtUtil.generate(Map.of("email", result.getEmail()), 10));

        result.setRefreshToken(jwtUtil.generate(Map.of("email", result.getEmail()), 60*24));

        log.info("Return: " + result);

        return result;
    }

}
