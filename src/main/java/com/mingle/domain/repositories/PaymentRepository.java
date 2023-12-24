package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.mingle.domain.entites.Payment;
import com.mingle.domain.entites.PaymentId;

public interface PaymentRepository extends JpaRepository<Payment, String>, JpaSpecificationExecutor<Payment>{

//	@Query("select p from Payment p join p.service s on p.serviceId= s.id  where p.memberId=:memberId")
//	List<Payment> selectById(String memberId);
}
