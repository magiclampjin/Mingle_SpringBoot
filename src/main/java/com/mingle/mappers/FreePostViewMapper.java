package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.FreePostView;
import com.mingle.dto.PostViewDTO;

@Mapper(componentModel = "spring")
public interface FreePostViewMapper extends GenericMapper<PostViewDTO, FreePostView>{

}
