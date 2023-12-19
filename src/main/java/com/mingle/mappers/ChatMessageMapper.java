package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.ChatMessage;
import com.mingle.dto.ChatMessageDTO;

@Mapper(componentModel = "spring")
public interface ChatMessageMapper extends GenericMapper<ChatMessageDTO, ChatMessage>{

}
