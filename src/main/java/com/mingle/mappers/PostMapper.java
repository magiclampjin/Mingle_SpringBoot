package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Post;
import com.mingle.dto.PostDTO;
import com.mingle.dto.UploadPostDTO;

@Mapper(componentModel = "spring")
public interface PostMapper extends GenericMapper<PostDTO, Post>{

	Post toEntity(UploadPostDTO dto);
}
