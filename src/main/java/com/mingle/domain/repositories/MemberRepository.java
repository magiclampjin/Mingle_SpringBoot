package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.Member;

public interface MemberRepository extends JpaRepository<Member, String>{
	// 로그인한 사용자 nickName 불러오기
	@Query("select m.nickname from Member m where m.id = :id")
	String selectUserNickName(@Param("id") String id);
	
	// 아이디 중복검사
	@Query("select count(*) from Member m where m.id=:id")
	Long countById(@Param("id") String id);

	default boolean idDuplicateCheck(String id) {
	    return countById(id) > 0;
	}
}
