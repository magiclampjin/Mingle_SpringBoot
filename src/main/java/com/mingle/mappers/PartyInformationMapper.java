package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.PartyInformation;
import com.mingle.dto.PartyInformationDTO;

@Mapper(componentModel = "spring")
public interface PartyInformationMapper  extends GenericMapper<PartyInformationDTO, PartyInformation>{

}