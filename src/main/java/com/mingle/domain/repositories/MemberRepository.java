package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.Bank;
import com.mingle.domain.entites.Member;
import com.mingle.dto.MemberDTO;

public interface MemberRepository extends JpaRepository<Member, String> {
	// 로그인한 사용자 nickName 불러오기
	@Query("select m from Member m where m.id = :id")
	Member userBasicInfo(@Param("id") String id);

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
	
	// 닉네임 중복 검사
	@Query("select count(*) from Member m where m.nickname=:nickname")
	Long countByNickname(@Param("nickname")String nickname);
	
	default boolean nickDuplicateCheck(String nickname) {
		return countByNickname(nickname) > 0;
	}

	// 멤버 이메일, 휴대폰 가져오기
	@Query("select m from Member m where m.id =:id")
	Member selectMypageInfo(@Param("id") String id);
	
	// 닉네임으로 엔티티 가져오기
	Member findAllById(String username);
	
	// 권한 가져오기
	@Query("select count(*) from Member m where m.id = :id and m.roleId = 'ROLE_ADMIN'")
	Long selectAuthority(@Param("id") String id);
	
	default boolean isAdmin(String id) {
		return selectAuthority(id) > 0;
	}

	// 이름과 메일 정보가 일치하는 사용자가 있는지 검증
	Member findByNameAndEmail(String name, String email);
	
	default boolean userVerification(MemberDTO dto) {
		return findByNameAndEmail(dto.getName(), dto.getEmail())!=null?true:false;
	}
	
	// 아아디, 이름, 이메일
	Member findByIdAndNameAndEmail(String id, String name, String email);
	
	default boolean userPWVerification(MemberDTO dto) {
		return findByIdAndNameAndEmail(dto.getId(),dto.getName(), dto.getEmail())!=null?true:false;
	}
	
	// 로그인한 사용자의 name불러오기
	@Query("select m.name from Member m where m.id=:userId")
	String selectUserName(String userId);
	
	// 로그인한 사용자의 밍글머니 불러오기
	@Query("select m.mingleMoney from Member m where m.id=:id")
	int selectMingleMoney(String id);
}
