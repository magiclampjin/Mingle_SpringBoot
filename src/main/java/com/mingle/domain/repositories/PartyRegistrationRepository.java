package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.PartyRegistration;

public interface PartyRegistrationRepository extends JpaRepository<PartyRegistration, Long>{

}