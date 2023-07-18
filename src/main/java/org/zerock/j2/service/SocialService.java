package org.zerock.j2.service;

import jakarta.transaction.Transactional;

@Transactional
public interface SocialService {

    String getKakaoEmail(String authCode);

}
