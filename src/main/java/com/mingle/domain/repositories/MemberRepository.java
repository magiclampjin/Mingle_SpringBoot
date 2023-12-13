package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
	@Query("select m.nickname from Member m where m.id = :id")
	String selectUserNickName(@Param("id") String id);
	
}
