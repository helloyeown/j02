package org.zerock.j2.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member_cart", indexes = @Index(columnList = "email, cno"))
public class MemberCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cno;
    private String email;
    private Long pno;

    // 시간이 추가되어야 함

}
