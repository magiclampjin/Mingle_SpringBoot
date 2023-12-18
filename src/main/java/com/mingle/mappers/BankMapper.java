package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Bank;
import com.mingle.dto.BankDTO;

@Mapper(componentModel = "spring")
public interface BankMapper extends GenericMapper<BankDTO, Bank>{
	
}
