package com.timescape.exception;

import org.springframework.security.core.AuthenticationException;

@SuppressWarnings("serial")
public class JWTAuthenticationException extends AuthenticationException {

	public JWTAuthenticationException(String msg) {
		super(msg);
	}
	
	public JWTAuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
