package com.mingle.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mingle.domain.entites.PaymentId;
import com.mingle.dto.PaymentDTO;
import com.mingle.services.PaymentService;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
	
	@Autowired
	private PaymentService pServ;
	
	// 결제 내역 목록 불러오기
//	@GetMapping
//	public ResponseEntity<List<PaymentDTO>> selectAll(Authentication authentication) {
//	    PaymentId pId = new PaymentId();
//	    pId.setMemberId(authentication.getName());
//	    
//	    System.out.println("pid : "+pId.getMemberId());
//	    
//	    List<PaymentDTO> list = pServ.selectById(pId.getMemberId());
//	    
//	   
//	    return ResponseEntity.ok(list);
//	}
	
	// 동적 검색
	@GetMapping("/searchBy")
	public ResponseEntity<List<PaymentDTO>> selectBySearch(
			@RequestParam(value="serviceId", required = false) String service,
            @RequestParam(value="paymentTypeId",required = false) String type,
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            Authentication authentication){
		
	
		System.out.println("서비스 : "+service);
		System.out.println("결제 타입 : "+type);
		System.out.println("시작 : "+start);
		System.out.println("끝 : "+end);
		
		if(service.equals("전체")) {
			service=null;
		}
		
		if(type.equals("전체")) {
			type=null;
		}
		
		List<PaymentDTO> searchResults = null;
		
		// String으로 입력받은 start와 end를 Timestamp로 변환해주어야함
		try {
	        Timestamp startTimestamp = convertToTimestamp(start);
	        Timestamp endTimestamp = convertToTimestamp(end);

	        // 이제 startTimestamp와 endTimestamp를 사용하여 검색 또는 다른 로직 수행

	        searchResults = pServ.findSearch(authentication.getName(), service, type, startTimestamp, endTimestamp);
	    } catch (ParseException e) {
	        e.printStackTrace();
	        // 예외 처리 로직 추가: 사용자에게 잘못된 날짜 형식을 알릴 수 있습니다.
	    }
		
		 //List<PaymentDTO> searchResults = pServ.findSearch(authentication.getName(),service,type,start,end);
		 
		 return ResponseEntity.ok(searchResults);
		
	}
	
	// timestamp변환 코드
	private Timestamp convertToTimestamp(String dateString) throws ParseException {
	    if (dateString != null && !dateString.isEmpty()) {
	        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date parsedDate = dateFormat.parse(dateString);
	        return new Timestamp(parsedDate.getTime());
	    }
	    return null;
	
	}
}
