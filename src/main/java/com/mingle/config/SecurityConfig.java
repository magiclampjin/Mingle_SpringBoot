package com.mingle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mingle.services.SecurityService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private SecurityService sServ;
	
	@Bean	
	protected SecurityFilterChain config(HttpSecurity http) throws Exception{
		http.csrf().disable();
		
		http.authorizeHttpRequests()
		.requestMatchers(new AntPathRequestMatcher("/**")).permitAll();
		
		// 로그인 ( 참고해도 되고 걍 지우고 다시 써도 무관.. )
		http.formLogin().loginProcessingUrl("/api/member/login").defaultSuccessUrl("/")
		// id input tag name
		.usernameParameter("id")
		// pw input tag name
		.passwordParameter("pw")
		// 로그인 성공
		.successHandler((request, response, authentication) -> {
			response.setStatus(HttpServletResponse.SC_OK);
		})
		// 로그인 실패
		.failureHandler((request, response, exception) -> { 
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		});
		
	
		// 인증이 되어있지 않을 때 발생하는 예외 처리
		http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		});
		
		// 접근 권한이 없을 때 이동할 페이지
		http.exceptionHandling().accessDeniedPage("/denied");
		
		// 로그아웃 1 - 기본 url 사용 
		//http.logout().invalidateHttpSession(true).logoutSuccessUrl("/");
		
		// 로그아웃 2 - 특정 url 사용
		http.logout().logoutUrl("/api/member/logout").invalidateHttpSession(true)
		.logoutSuccessHandler((request, response, authentication) -> {
			response.setStatus(HttpServletResponse.SC_OK);
		});
		
		http.userDetailsService(sServ);
		return http.build();
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
}