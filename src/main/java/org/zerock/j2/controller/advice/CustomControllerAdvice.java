package org.zerock.j2.controller.advice;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zerock.j2.service.MemberServiceImpl;

import java.util.Map;

@RestControllerAdvice
@Log4j2
public class CustomControllerAdvice {

    @ExceptionHandler(MemberServiceImpl.MemberLoginException.class)
    public ResponseEntity<Map<String, String>> handleException(MemberServiceImpl.MemberLoginException e) {

        log.info("----------------------");
        log.info(e.getMessage());

        return ResponseEntity.status(HttpStatus.OK).body(Map.of("errorMsg", "Login Fail"));

    }

}
