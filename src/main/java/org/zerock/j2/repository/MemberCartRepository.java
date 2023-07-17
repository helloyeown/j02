package org.zerock.j2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.j2.entity.MemberCart;

import java.util.List;

public interface MemberCartRepository extends JpaRepository<MemberCart, Long> {

    // 동적 쿼리가 아니기 때문에 간단하게 쿼리 메소드 사용
    @Query("select mc from MemberCart mc where mc.email = :email order by mc.cno asc")
    List<MemberCart> selectCart(@Param("email") String email);

}
