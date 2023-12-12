package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.ServiceCategory;
import com.mingle.dto.ServiceCategoryDTO;

@Mapper(componentModel = "spring")
public interface ServiceCategoryMapper extends GenericMapper<ServiceCategoryDTO, ServiceCategory>{
	
}
