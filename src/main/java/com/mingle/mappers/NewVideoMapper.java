package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.NewVideo;
import com.mingle.dto.NewVideoDTO;

@Mapper(componentModel = "spring")
public interface NewVideoMapper extends GenericMapper<NewVideoDTO, NewVideo>{
	
}
