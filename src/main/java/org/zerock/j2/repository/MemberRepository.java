package org.zerock.j2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j2.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {



}
