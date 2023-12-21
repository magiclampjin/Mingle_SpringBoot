package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.TodayCalculationParty;
import com.mingle.dto.TodayCalculationPartyDTO;

@Mapper(componentModel = "spring")
public interface TodayCalculationPartyMapper extends GenericMapper<TodayCalculationPartyDTO, TodayCalculationParty>{
	
}