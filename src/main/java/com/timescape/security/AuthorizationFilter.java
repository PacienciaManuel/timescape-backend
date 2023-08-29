package com.timescape.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.timescape.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	private final JWTService jwtService;
	
	public AuthorizationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);		
		this.jwtService = jwtService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {		
    	String authHeader = request.getHeader(JWTService.HEADER_AUTHORIZATION);
    	
    	if (authHeader == null) {
    		chain.doFilter(request, response);
    		return;
		}
    	
		UsernamePasswordAuthenticationToken athenticationToken = new UsernamePasswordAuthenticationToken(jwtService.exctract(authHeader).getEmail(), null, new ArrayList<>());
		SecurityContextHolder.getContext().setAuthentication(athenticationToken);
		chain.doFilter(request, response);
	}
}
