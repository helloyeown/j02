package org.zerock.j2.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Log4j2
public class JwtUtil {

    public static class CustomJWTException extends RuntimeException {
        public CustomJWTException(String msg){
            super(msg);
        }
    }

    @Value("${org.zerock.jwt.secret}")
    private String key;

    public String generate(Map<String, Object> claimMap, int min){     // 만료시간

        // 헤더
        Map<String, Object> headers = new HashMap<>();
        headers.put("type", "JWT");

        // calims
        Map<String, Object> claims = new HashMap<>();
        // 입력으로 받은 클레임들을 JWT의 페이로드에 포함
        claims.putAll(claimMap);

        // 서명에 사용될 비밀키
        SecretKey key = null;

        try {
            key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {

        }

        String jwtStr = Jwts.builder()
                .setHeader(headers)
                .setClaims(claims)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))    // 현재시간
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)      // 비밀키로 서명 생성
                .compact();

        return jwtStr;
    }

    // 예외처리
    public Map<String, Object> validateToken(String token){

        Map<String, Object> claims = null;

        if(token == null) {
            throw new CustomJWTException("NullToken");
        }

        try {
            SecretKey key = Keys.hmacShaKeyFor(this.key.getBytes(StandardCharsets.UTF_8));

            claims = Jwts.parserBuilder().setSigningKey(key).build()
                    .parseClaimsJws(token).getBody();

        } catch (MalformedJwtException e) {     // jwt 문자열 구성이 잘못됨
            throw new CustomJWTException("MalFormed");
        } catch (ExpiredJwtException e) {       //만료
            throw new CustomJWTException("Expired");
        } catch (InvalidClaimException e) {     // 잘못된 구성
            throw new CustomJWTException("Invalid");
        } catch (JwtException e) {              // 그 외
            throw new CustomJWTException(e.getMessage());
        } catch (Exception e) {                 // 알 수 없는 오류
            throw new CustomJWTException("Error");
        }

        return claims;

    }

}
