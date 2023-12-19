package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.ChatRoom;
import com.mingle.dto.ChatRoomDTO;

@Mapper(componentModel = "spring")
public interface ChatRoomMapper extends GenericMapper<ChatRoomDTO, ChatRoom>{

}
