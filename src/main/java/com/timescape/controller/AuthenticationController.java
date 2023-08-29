package com.timescape.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timescape.dto.AuthenticationResponse;
import com.timescape.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	private final AuthenticationService authenticationService;
	
	@PostMapping("/refresh")
	public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader("Authorization") String authorization) {
		return ResponseEntity.ok(authenticationService.refreshToken(authorization));
	}
	
}
