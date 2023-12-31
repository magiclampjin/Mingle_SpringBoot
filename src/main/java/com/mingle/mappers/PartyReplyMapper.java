package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.PartyReply;
import com.mingle.dto.PartyReplyDTO;

@Mapper(componentModel = "spring")
public interface PartyReplyMapper extends GenericMapper<PartyReplyDTO, PartyReply>{

}
