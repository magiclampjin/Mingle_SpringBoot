package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Report;
import com.mingle.dto.ReportDTO;

@Mapper(componentModel = "spring")
public interface ReportMapper extends GenericMapper<ReportDTO, Report> {
	
}
