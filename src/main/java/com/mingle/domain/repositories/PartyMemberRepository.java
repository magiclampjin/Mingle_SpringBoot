package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.PartyMember;

public interface PartyMemberRepository  extends JpaRepository<PartyMember, Long>{

	@Query("select count(*) from PartyMember pm where pm.memberId=:userId")
	Long isMemberParty(@Param("userId") String userId);
	
	default boolean isAlreadyMember(String userId) {
		return isMemberParty(userId)>0;
	}
}
