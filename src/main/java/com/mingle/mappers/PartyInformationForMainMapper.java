package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.PartyInformationForMain;
import com.mingle.domain.entites.PartyInformation;
import com.mingle.dto.PartyInformationForMainDTO;
import com.mingle.dto.PartyInformationDTO;

@Mapper(componentModel = "spring")
public interface PartyInformationForMainMapper extends GenericMapper<PartyInformationForMainDTO, PartyInformationForMain>{

}
