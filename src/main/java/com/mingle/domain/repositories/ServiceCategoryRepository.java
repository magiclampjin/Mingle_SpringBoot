package com.mingle.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mingle.domain.entites.ServiceCategory;

public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, String>{
	
}
