package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.PaymentAccount;

public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Long>{

	// 등록된 계좌 불러오기
	PaymentAccount findAllByMemberId(String userId);
	
	
}
