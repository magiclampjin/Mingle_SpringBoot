package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.Bank;
import com.mingle.domain.entites.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	// 로그인한 사용자 nickName 불러오기
	@Query("select m.nickname from Member m where m.id = :id")
	String selectUserNickName(@Param("id") String id);

	// 아이디 중복검사
	@Query("select count(*) from Member m where m.id=:id")
	Long countById(@Param("id") String id);

	default boolean idDuplicateCheck(String id) {
		return countById(id) > 0;
	}

	// 이메일 중복검사
	@Query("select count(*) from Member m where m.email=:email")
	Long countByEmail(@Param("email") String email);

	default boolean emailDuplicateCheck(String email) {
		return countByEmail(email) > 0;
	}

	// 전화번호 중복검사
	@Query("select count(*) from Member m where m.phone=:phone")
	Long countByPhone(@Param("phone") String phone);

	default boolean phoneDuplicateCheck(String phone) {
		return countByPhone(phone) > 0;
	}
	
	// 멤버 이메일, 휴대폰 가져오기
	@Query("select m from Member m where m.id =:id")
	Member selectMypageInfo(@Param("id") String id);
	
	// 닉네임으로 엔티티 가져오기
	Member findAllById(String username);
	
}
