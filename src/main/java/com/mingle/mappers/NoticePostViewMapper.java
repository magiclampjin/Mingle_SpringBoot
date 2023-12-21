package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.NoticePostView;
import com.mingle.dto.PostViewDTO;


@Mapper(componentModel = "spring")
public interface NoticePostViewMapper extends GenericMapper<PostViewDTO, NoticePostView>{

}
