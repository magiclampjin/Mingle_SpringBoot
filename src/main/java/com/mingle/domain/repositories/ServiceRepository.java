package com.mingle.domain.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.Service;

public interface ServiceRepository extends JpaRepository<Service, Long>{
	List<Service> findByServiceCategoryId(String id);
}
