package com.mingle.mappers;

import org.mapstruct.Mapper;

import com.mingle.domain.entites.Payment;
import com.mingle.dto.PaymentDTO;

@Mapper(componentModel="spring")
public interface PaymentMapper extends GenericMapper<PaymentDTO, Payment>{

}
