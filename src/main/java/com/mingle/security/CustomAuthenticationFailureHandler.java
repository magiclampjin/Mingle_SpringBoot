package com.mingle.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 실패 시 로그로 출력
        logger.error("Authentication failed: {}", exception.getMessage());
        
        // 정지된 계정으로 로그인
        if ("유효하지 않은 사용자입니다.".equals(exception.getMessage())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        // 아이디/비밀번호 잘못 입력
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        // 클라이언트에게 메시지 전달 (옵션)
        response.getWriter().println("Authentication failed: " + exception.getMessage());
    }
}