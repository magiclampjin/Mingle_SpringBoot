package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.ReportParty;
import com.mingle.dto.ReportPartyDTO;

@Mapper(componentModel = "spring")
public interface ReportPartyMapper extends GenericMapper<ReportPartyDTO, ReportParty> {

}
