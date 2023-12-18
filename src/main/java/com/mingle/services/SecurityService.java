package com.mingle.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mingle.domain.entites.Member;
import com.mingle.domain.repositories.MemberRepository;
import com.mingle.security.SecurityUser;

@Service
public class SecurityService implements UserDetailsService{
	@Autowired
	private MemberRepository mRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loginID : "+username);
		Member m = mRepo.findById(username).get();
		SecurityUser user = new SecurityUser(m);
		System.out.println(user.toString());
		return user;
	}
}
