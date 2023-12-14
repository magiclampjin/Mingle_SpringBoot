package com.mingle.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.mingle.dto.NewVideoDTO;
import com.mingle.services.NewVideoService;


//각종 외부 api요청을 보내고 되돌아 온 값을 사이트로 보내주는 컨트롤러

@RestController
@RequestMapping("/api/external/")
public class ApiController {
	
	@Autowired
	private NewVideoService nvServ;

	@GetMapping("youtube/latestvideo")
	public ResponseEntity<List<NewVideoDTO>> getLatestLikestVideos() throws IOException {
	        return ResponseEntity.ok(nvServ.selectLikestVideosDuringLatestOneMonth());
	}
	
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e, WebRequest request) {
        // 로그 기록, 에러 메시지 생성 등 필요한 처리를 수행
        // 여기서는 예외 메시지를 반환
    	e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred: " + e.getMessage());
    }
	

	

}
