package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mingle.domain.entites.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>{
	List<Service> findByServiceCategoryId(String id);
	
	@Query(value = "select id from join_party_list WHERE service_category_id = :service_category_id and member_id = :member_id", nativeQuery = true)
	List<Integer> selectByServiceCategoryIdAndIsJoin(@Param("service_category_id")String service_category_id, @Param("member_id")String member_id);
	
	@Query(value = "select id from join_party_list WHERE member_id = :member_id", nativeQuery = true)
	List<Integer> selectByAllAndIsJoin(@Param("member_id")String member_id);
	
}
