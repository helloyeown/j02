package org.zerock.j2.util;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Objects;

@SpringBootTest
@Log4j2
public class JwtTests {

    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreate() {

        Map<String, Object> claim = Map.of("email", "user00");

        String jwtStr = jwtUtil.generate(claim, 10);

        log.info(jwtStr);

    }

    @Test
    public void testToken() {

        String token = "eyJ0eXBlIjoiSldUIiwiYWxnIjoiSFMyNTYifQ.eyJlbWFpbCI6InVzZXIwMCIsImlhdCI6MTY4OTc0NDM4NCwiZXhwIjoxNjg5NzQ0OTg0fQ.CeQyrIQbjUOUT_O5R2w2mx1x4qV5RjMoY4QPhZbTvQA";

        try {
            jwtUtil.validateToken(token);
        } catch (Exception e) {
            log.info(e);
        }

    }

}
