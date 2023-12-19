package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.Bank;

public interface BankRepository extends JpaRepository<Bank, String>{

	// 은행 목록 가져오기
	List<Bank> findAll();
}
