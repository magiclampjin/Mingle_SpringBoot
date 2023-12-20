package com.mingle.specification;

import java.sql.Timestamp;

import org.springframework.data.jpa.domain.Specification;

import com.mingle.domain.entites.Payment;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class PaymentSpecification {
	
	// 내 아이디는 무조건 where로 검색되어야 함
	public static Specification<Payment> findByMemberId(String memberId) {
		return new Specification<Payment>() {
			@Override
			public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get("memberId"), memberId);
			}
		};
	}
	
	// 서비스 명 검색
	  public static Specification<Payment> findByServiceId(String serviceId) {
	        return new Specification<Payment>() {
	        	@Override
	        	public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query,
	        			CriteriaBuilder criteriaBuilder) {
	        		// TODO Auto-generated method stub
	        		return criteriaBuilder.equal(root.get("serviceId"), serviceId);
	        	}
	        };
	    }
	  
	  // 결제 타입 검색
	  public static Specification<Payment> findByPaymentTypeId(String paymentTypeId){
		  return new Specification<Payment>() {
			  @Override
			public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.equal(root.get("paymentTypeId"), paymentTypeId);
			}
		  };
	  }
	  
	  // 기간 검색
	  public static Specification<Payment> findByDate(Timestamp start, Timestamp end){
		  return new Specification<Payment>() {
			  @Override
			public Predicate toPredicate(Root<Payment> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				return criteriaBuilder.between(root.get("date"), start, end);
			}
		  };
	  }
	  

}
