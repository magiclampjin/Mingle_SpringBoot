package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.ReportReply;
import com.mingle.dto.ReportReplyDTO;

@Mapper(componentModel = "spring")
public interface ReportReplyMapper extends GenericMapper<ReportReplyDTO, ReportReply> {

}
