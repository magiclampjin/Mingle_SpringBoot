package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Post;
import com.mingle.dto.PostDTO;

@Mapper(componentModel = "spring")
public interface PostMapper extends GenericMapper<PostDTO, Post>{

}
