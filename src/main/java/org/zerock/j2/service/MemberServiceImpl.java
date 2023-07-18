package org.zerock.j2.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.zerock.j2.dto.MemberDTO;
import org.zerock.j2.entity.Member;
import org.zerock.j2.repository.MemberRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    // 예외 생성
    // unckecked
    public static final class MemberLoginException extends RuntimeException {

        public MemberLoginException(String msg){
            super(msg);
        }

    }

    @Override
    public MemberDTO login(String email, String pw) {

        MemberDTO memberDTO = null;

        try {
            Optional<Member> result = memberRepository.findById(email);

            Member member = result.orElseThrow();   // throw를 던지면 catch로 감

            if (!member.getPw().equals(pw)) {
                throw new MemberLoginException("Password Incorrect");
            }

            memberDTO = MemberDTO.builder()
                    .email(member.getEmail())
                    .pw("")
                    .nickname(member.getNickname())
                    .admin(member.isAdmin())
                    .build();

        } catch (Exception e) {
            throw new MemberLoginException(e.getMessage());
        }

        return memberDTO;

    }

    @Override
    public MemberDTO getMemberWithEmail(String email) {

        Optional<Member> result = memberRepository.findById(email);

        // 데이터가 존재한다면 (기존 회원)
        if(result.isPresent()){
            Member member = result.get();

            MemberDTO dto = MemberDTO.builder()
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .admin(member.isAdmin())
                    .build();

            return dto;
        }

        // 디비에 존재하지 않는 이메일일 경우 (소셜 회원)
        Member socialMember = Member.builder()
                .email(email)
                .pw(UUID.randomUUID().toString())
                .nickname("SOCIAL_MEMBER")
                .build();

        memberRepository.save(socialMember);

        MemberDTO dto = MemberDTO.builder()
                .email(socialMember.getEmail())
                .nickname(socialMember.getNickname())
                .admin(socialMember.isAdmin())
                .build();

        return null;
    }

}
