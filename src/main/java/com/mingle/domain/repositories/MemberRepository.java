package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.Member;

public interface MemberRepository extends JpaRepository<Member, String>{

}
