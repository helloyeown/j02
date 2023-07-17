package org.zerock.j2.service;

import jakarta.transaction.Transactional;
import org.zerock.j2.dto.MemberDTO;

@Transactional
public interface MemberService {

    MemberDTO login(String email, String pw);

}
