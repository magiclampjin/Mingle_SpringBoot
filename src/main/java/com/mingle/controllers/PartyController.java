package com.mingle.controllers;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.dto.PartyInformationDTO;
import com.mingle.dto.ServiceCategoryDTO;
import com.mingle.dto.ServiceDTO;
import com.mingle.services.PartyService;

@RestController
@RequestMapping("/api/party")
public class PartyController {
	
	@Autowired
	private PartyService pServ ;
	
	// 제공하는 서비스 카테고리명 불러오기
	@GetMapping
	public ResponseEntity<List<ServiceCategoryDTO>> selectCategoryAll() {
		List<ServiceCategoryDTO> list = pServ.selectCategoryAll();
		return ResponseEntity.ok(list);
	}

	// 카테고리별 서비스 정보 불러오기
	@GetMapping("/getService/{id}")
	public ResponseEntity<List<ServiceDTO>> selectServiceByCategoryId(@PathVariable String id) {
		List<ServiceDTO> list = pServ.selectServiceByCategoryId(id);
		return ResponseEntity.ok(list);
	}
	
	// 특정 서비스 정보 불러오기
	@GetMapping("/getServiceById/{id}")
	public ResponseEntity<ServiceDTO> selectServiceByServiceId(@PathVariable Long id) {
		ServiceDTO dto = pServ.selectServiceByServiceId(id);
		return ResponseEntity.ok(dto);
	}
	
	// 파티 정보 저장, 파티 등록, 파티장 등록
	@PostMapping("/auth")
	public ResponseEntity<Void> inertParty (@RequestBody PartyInformationDTO partyData, Authentication authentication){
		pServ.inertParty(partyData, authentication.getName());
		return ResponseEntity.ok().build();
	}
		
//	// 가입한 파티 목록 불러오기
//	@GetMapping("/getMyPartyList")
//	public ResponseEntity<List<PartyInformationDTO>> getMypartyList(Authentication authentication){
//		List<PartyInformationDTO> list = pServ.getMypartyList(authentication.getName());
//		return ResponseEntity.ok(list);
//	}
	
	// 생성된 파티 목록 불러오기
	@GetMapping("/getPartyList/{id}")
	public ResponseEntity<List<PartyInformationDTO>> getPartyList(@PathVariable Long id){
		List<PartyInformationDTO> list = pServ.selectPartyList(id);
		return ResponseEntity.ok(list);
	}
	
	// 등록된 파티 정보 중 선택한 날짜에 해당하는 파티 정보 불러오기
	@GetMapping("/getPartyListByStartDate/{id}")
	public ResponseEntity<List<PartyInformationDTO>> selectPartyListByStartDate(@PathVariable Long id, @RequestParam Instant start, @RequestParam Instant end){
		List<PartyInformationDTO> list = pServ.selectPartyListByStartDate(id, start, end);
		return ResponseEntity.ok(list);
	}
	
	@ExceptionHandler(Exception.class)
	public String exceptionHandler(Exception e) {
		e.printStackTrace();
		return "error";
	}
}
