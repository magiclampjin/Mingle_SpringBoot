package com.mingle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.mingle.services.CustomOAuth2UserService;
import com.mingle.services.PrincipalOauth2UserService;
import com.mingle.services.SecurityService;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private SecurityService sServ;
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Autowired
	private CustomOAuth2UserService customOAuth2UserService;
	
	@Bean	
	protected SecurityFilterChain config(HttpSecurity http) throws Exception{
		//h2-console화면을 사용하기 위해 해당 옵션을 disable
		http.csrf().disable().headers().frameOptions().disable();
		
		http.authorizeHttpRequests()
		//.requestMatchers(new AntPathRequestMatcher("/party/PartyCreatePage/**")).authenticated()
		.requestMatchers(new AntPathRequestMatcher("/uploads/**")).permitAll()
		.requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole("ADMIN")
		.requestMatchers(new AntPathRequestMatcher("/api/party/auth/**")).authenticated()
		.requestMatchers(new AntPathRequestMatcher("/**")).permitAll();
		
		// 로그인 
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
		})
		.and().oauth2Login()
		.userInfoEndpoint().userService(customOAuth2UserService);

		
	
		// 인증이 되어있지 않을 때 발생하는 예외 처리
		http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		}).accessDeniedPage("/denied");// 접근 권한이 없을 때 이동할 페이지
			
		
		// 로그아웃 - 특정 url 사용
		http.logout().logoutUrl("/api/member/logout").invalidateHttpSession(true)
		.logoutSuccessHandler((request, response, authentication) -> {
			response.setStatus(HttpServletResponse.SC_OK);
		});
		
//		http.userDetailsService(sServ);
		return http.build();
	}
	
	@Bean
	protected PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	// 탈퇴 시 로그아웃에 필요
	@Bean
	public LogoutHandler securityContextLogoutHandler() {
	    return new SecurityContextLogoutHandler();
	}

	
//	private CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.addAllowedOrigin("http://localhost:3000"); // 허용할 출처
//        configuration.addAllowedMethod("*");
//        configuration.addAllowedHeader("*");
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return (CorsConfigurationSource) source;
//    }
}
