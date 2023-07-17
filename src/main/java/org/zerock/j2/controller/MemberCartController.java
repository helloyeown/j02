package org.zerock.j2.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.service.MemberCartService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@CrossOrigin
@RequestMapping("/api/cart/")
public class MemberCartController {

    private final MemberCartService cartService;

    @PostMapping("add")
    public List<MemberCartDTO> add(@RequestBody MemberCartDTO memberCartDTO){
        // 담았던 목록 데이터 리턴

        log.info("param: " + memberCartDTO);

        return cartService.addCart(memberCartDTO);
    }

    @GetMapping("{email}")
    public List<MemberCartDTO> get(@PathVariable String email){

        return cartService.getCart(email);
    }

}
