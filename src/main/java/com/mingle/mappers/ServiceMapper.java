package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Service;
import com.mingle.dto.ServiceDTO;

@Mapper(componentModel = "spring")
public interface ServiceMapper extends GenericMapper<ServiceDTO, Service>{

}
