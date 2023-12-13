package com.mingle.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
		System.out.println(list);
		return ResponseEntity.ok(list);
	}
}
