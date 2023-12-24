package com.mingle.controllers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.mingle.dto.PaymentDTO;
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

//	// 카테고리별 서비스 정보 불러오기
//	@GetMapping("/getService/{id}")
//	public ResponseEntity<List<ServiceDTO>> selectServiceByCategoryId(@PathVariable String id) {
//		List<ServiceDTO> list = pServ.selectServiceByCategoryId(id);
//		return ResponseEntity.ok(list);
//	}
	
	// 카테고리별 서비스 정보 & 가입한 서비스 정보 불러오기
	@GetMapping("/getService/{id}")
	public ResponseEntity<Map<String, Object>> selectServiceByCategoryId(@PathVariable String id, Authentication authentication) {
		List<ServiceDTO> list = pServ.selectServiceByCategoryId(id);
		
		// 로그인한 사용자일 경우 가입한 목록 가져오기
		List<Integer> joinList = new ArrayList<>();
		if(authentication != null) {
			joinList = pServ.selectServiceByIsJoin(id, authentication.getName());
		}
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("joinList", joinList);
		return ResponseEntity.ok(param);
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
	
	// 서비스 명 리스트 불러오기
	@GetMapping("/getServiceNameList")
	public ResponseEntity<List<ServiceDTO>> getServiceNameList(){
		List<ServiceDTO> dtos = pServ.getServiceNameList();
		return ResponseEntity.ok(dtos);
	}

	// 파티 가입 & 첫 달 결제 내역 저장  & 밍글 머니 사용
	@PostMapping("/auth/joinParty/{id}")
	public ResponseEntity<Void> joinParty(@PathVariable Long id, @RequestBody(required = false) PaymentDTO paymentData, Authentication authentication){
		pServ.insertJoinParty(id, authentication.getName(), paymentData);
		return ResponseEntity.ok().build();
	}
	
	// 메인에서 사용할 전체 서비스 리스트
	@GetMapping("/getServiceMainPage")
	public ResponseEntity<Map<String, Object>> getServiceMainPage(Authentication authentication) {
		List<ServiceDTO> list = pServ.selectServiceByCategoryId("전체");
		
		// 로그인한 사용자일 경우 가입한 목록 가져오기
		List<Integer> joinList = new ArrayList<>();
		if(authentication != null) {
			joinList = pServ.selectServiceByIsJoin("전체", authentication.getName());
		}
		Map<String, Object> param = new HashMap<>();
		param.put("list", list);
		param.put("joinList", joinList);
		return ResponseEntity.ok(param);
//		return ResponseEntity.ok(list);
	} 
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> exceptionHandler(Exception e) {
		e.printStackTrace();
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
	}
}
