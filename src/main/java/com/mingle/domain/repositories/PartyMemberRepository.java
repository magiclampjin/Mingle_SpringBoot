package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.PartyMember;

public interface PartyMemberRepository  extends JpaRepository<PartyMember, Long>{
	@Query("select m.memberId from PartyMember m where partyRegistrationId=:partyRegistrationId and isPartyManager = true")
	String selectMemberIdBypartyRegistrationIdAndIsPartyManagerTrue(@Param("partyRegistrationId") Long partyRegistrationId);

}
