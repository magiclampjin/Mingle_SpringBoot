package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.ReplyReactions;
import com.mingle.dto.ReplyReactionsDTO;

@Mapper(componentModel = "spring")
public interface ReplyReactionsMapper extends GenericMapper<ReplyReactionsDTO, ReplyReactions>{

}
