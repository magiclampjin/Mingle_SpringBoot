package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Reply;
import com.mingle.dto.ReplyDTO;
import com.mingle.dto.UploadReplyDTO;

@Mapper(componentModel = "spring")
public interface ReplyMapper extends GenericMapper<ReplyDTO, Reply>{

	Reply toEntity(UploadReplyDTO dto);
}
