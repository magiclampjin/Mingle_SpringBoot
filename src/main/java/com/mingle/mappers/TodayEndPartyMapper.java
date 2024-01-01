package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.TodayEndParty;
import com.mingle.dto.TodayEndPartyDTO;

@Mapper(componentModel = "spring")
public interface TodayEndPartyMapper extends GenericMapper<TodayEndPartyDTO, TodayEndParty>{
	
}