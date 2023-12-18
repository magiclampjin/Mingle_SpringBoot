package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.PartyMember;

public interface PartyMemberRepository  extends JpaRepository<PartyMember, Long>{

}
