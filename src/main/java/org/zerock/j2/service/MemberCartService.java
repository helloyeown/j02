package org.zerock.j2.service;

import jakarta.transaction.Transactional;
import org.zerock.j2.dto.MemberCartDTO;
import org.zerock.j2.entity.MemberCart;

import java.util.List;

@Transactional
public interface MemberCartService {

    List<MemberCartDTO> addCart(MemberCartDTO memberCartDTO);

    List<MemberCartDTO> getCart(String email);

    default MemberCart dtoToEntity(MemberCartDTO dto) {
        // dto를 엔티티로 바꿔주는 메소드 (ModelMapper 역할)
        MemberCart entity = MemberCart.builder()
                .cno(dto.getCno())
                .pno(dto.getPno())
                .email(dto.getEmail())
                .build();

        return entity;
    }

    default MemberCartDTO entityToDTO(MemberCart entity){

        MemberCartDTO dto = MemberCartDTO.builder()
                .cno(entity.getCno())
                .pno(entity.getPno())
                .email(entity.getEmail())
                .build();

        return dto;
    }

}
