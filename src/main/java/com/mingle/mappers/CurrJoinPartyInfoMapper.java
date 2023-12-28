package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.CurrJoinPartyInfo;
import com.mingle.dto.CurrJoinPartyInfoDTO;

@Mapper(componentModel = "spring")
public interface CurrJoinPartyInfoMapper extends GenericMapper<CurrJoinPartyInfoDTO, CurrJoinPartyInfo>{

}