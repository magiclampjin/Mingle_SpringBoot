package com.mingle.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.mingle.domain.entites.Member;


public class SecurityUser extends User{
	public SecurityUser(Member m) {
		super(m.getId(), m.getPassword() , m.isEnabled(), true, true, true, AuthorityUtils.createAuthorityList(m.getRoleId()));
	}
}
