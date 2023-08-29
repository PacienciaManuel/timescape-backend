package com.timescape.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.timescape.dto.AuthenticationResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

	@Autowired
	private final JWTService jwtService;
	
	public AuthenticationResponse refreshToken(String authorization) {
		return AuthenticationResponse
		.builder()
		.tokenAccesso(jwtService.generateRefreshToken(jwtService.exctract(authorization)))
		.build();
	}
}
