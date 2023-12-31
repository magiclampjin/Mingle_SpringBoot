package com.mingle.auth.dto;

import com.mingle.domain.entites.Member;

import lombok.Getter;

@Getter
public class SessionUser {
	private String name;
    private String email;

    public SessionUser(Member user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
