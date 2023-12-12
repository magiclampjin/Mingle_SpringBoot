package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Reply;
import com.mingle.dto.ReplyDTO;

@Mapper(componentModel = "spring")
public interface ReplyMapper extends GenericMapper<ReplyDTO, Reply>{

}
