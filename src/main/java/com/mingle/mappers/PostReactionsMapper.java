package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.PostReactions;
import com.mingle.dto.PostReactionsDTO;

@Mapper(componentModel = "spring")
public interface PostReactionsMapper extends GenericMapper<PostReactionsDTO, PostReactions>{

}
