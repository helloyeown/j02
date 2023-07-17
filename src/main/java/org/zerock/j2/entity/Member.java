package org.zerock.j2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Member {

    @Id
    private String email;

    private String pw;

    private String nickname;

    private boolean admin;  // 관리자인지 (디폴트 false)

}
