package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.PopularPostView;
import com.mingle.dto.PostViewDTO;

@Mapper(componentModel = "spring")
public interface PopularPostViewMapper extends GenericMapper<PostViewDTO, PopularPostView>{

}
