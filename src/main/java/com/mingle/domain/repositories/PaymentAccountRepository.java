package com.mingle.domain.repositories;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.PaymentAccount;

public interface PaymentAccountRepository extends JpaRepository<PaymentAccount, Long>{

	// 로그인한 사용자의 name불러오기
	@Query("select m.name from Member m where m.id=:userId")
	String selectUserName(@Param("userId") String userId);
	
	// 등록된 계좌 불러오기
	PaymentAccount findAllByMemberId(String userId);
	
	
}
