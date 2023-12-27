package com.mingle.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.PaymentAccount;
import com.mingle.domain.repositories.BankRepository;
import com.mingle.domain.repositories.PaymentAccountRepository;
import com.mingle.dto.BankDTO;
import com.mingle.dto.PaymentAccountDTO;
import com.mingle.mappers.BankMapper;
import com.mingle.mappers.PaymentAccountMapper;

@Service
public class PaymentAccountService {
	
	
	@Autowired
	private PaymentAccountRepository paRepo;
	
	@Autowired
	private PaymentAccountMapper paMapper;

	@Autowired
	private BankRepository bRepo;

	@Autowired
	private BankMapper bMapper;
	
	// 계좌등록
	public void insertAccountInfo(PaymentAccountDTO dto) {

		paRepo.save(paMapper.toEntity(dto));
		
	}
	
	// 등록된 계좌 불러오기
	public PaymentAccountDTO selectById(String userId) {
		
		return paMapper.toDto(paRepo.findAllByMemberId(userId));
	}
	
	// 등록된 계좌 삭제하기
	public void deleteById(String userId) {
		
		paRepo.deleteById(paRepo.findAllByMemberId(userId).getId());
	}
	
	// 등록된 계좌 수정하기
	public void updateById(String userId, PaymentAccountDTO dto) {
		
		PaymentAccount pa = paRepo.findById(paRepo.findAllByMemberId(userId).getId()).get();
		pa.setAccountNumber(dto.getAccountNumber());
		pa.setBankId(dto.getBankId());
		
		paRepo.save(pa);
	}
	
	// 은행 목록 불러오기
	public List<BankDTO> selectBank() {

		return bMapper.toDtoList(bRepo.findAll());
	}
}
