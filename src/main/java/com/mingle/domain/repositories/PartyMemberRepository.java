package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.PartyMember;

public interface PartyMemberRepository  extends JpaRepository<PartyMember, Long>{
	// 파티장 로그인 아이디 정보 가져오기
	@Query("select m.memberId from PartyMember m where partyRegistrationId=:partyRegistrationId and isPartyManager = true")
	String selectMemberIdBypartyRegistrationIdAndIsPartyManagerTrue(@Param("partyRegistrationId") Long partyRegistrationId);

	// 가입한 파티 있는 지 확인
	@Query(value="select count(*) from party_member pm join party_information pi on pm.party_registration_id = pi.id where member_id=\"test0002\" and DATE_ADD(start_date, INTERVAL month_count MONTH) >= CURRENT_DATE()", nativeQuery = true)
	Long isMemberParty(@Param("userId") String userId);
	
	default boolean isAlreadyMember(String userId) {
		return isMemberParty(userId)>0;
	}
	
	// 파티 삭제를 위해 가입한 파티원 있는 지 체크
	@Query("select count(*) from PartyMember pm where pm.partyRegistrationId=:id group by pm.partyRegistrationId")
	int selectCntById(Long id);
	
	
	// 신고 대상자가 파티 회원이 맞는 지 확인
	@Query("select count(*) from PartyMember pm where pm.memberId=:userId and  partyRegistrationId=:partyRegistrationId")
	Long isMemberPartyAttending(@Param("userId") String userId, @Param("partyRegistrationId") Long partyRegistrationId);
	default boolean isAlreadyMemberAttendig(String userId, Long partyRegistrationId) {
		return isMemberPartyAttending(userId, partyRegistrationId)>0;
	}
}
