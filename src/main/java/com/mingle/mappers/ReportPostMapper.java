package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.ReportPost;
import com.mingle.dto.ReportPostDTO;

@Mapper(componentModel = "spring")
public interface ReportPostMapper extends GenericMapper<ReportPostDTO, ReportPost> {
	
}