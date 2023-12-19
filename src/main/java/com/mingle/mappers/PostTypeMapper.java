package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.PostType;
import com.mingle.dto.PostTypeDTO;

@Mapper(componentModel = "spring")
public interface PostTypeMapper extends GenericMapper<PostTypeDTO, PostType>{

}
