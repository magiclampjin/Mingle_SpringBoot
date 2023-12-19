package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.PostFile;
import com.mingle.dto.PostFileDTO;

@Mapper(componentModel = "spring")
public interface PostFileMapper extends GenericMapper<PostFileDTO, PostFile>{

}
