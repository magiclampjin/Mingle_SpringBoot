package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.PaymentAccount;
import com.mingle.dto.PaymentAccountDTO;

@Mapper(componentModel = "spring")
public interface PaymentAccountMapper extends GenericMapper<PaymentAccountDTO, PaymentAccount>{

}
