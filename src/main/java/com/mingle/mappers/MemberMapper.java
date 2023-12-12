package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Member;
import com.mingle.dto.MemberDTO;

@Mapper(componentModel = "spring")
public interface MemberMapper extends GenericMapper<MemberDTO, Member>{

}
