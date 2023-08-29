package com.timescape.security;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.timescape.dto.AuthenticationFailureResponse;
import com.timescape.dto.AuthenticationResponse;
import com.timescape.exception.JWTAuthenticationException;
import com.timescape.model.Usuario;
import com.timescape.service.JWTService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter implements AuthenticationFailureHandler {
	private final AuthenticationManager authenticationManager;
	private final JWTService jwtService;
	
	public AuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
		super(authenticationManager);
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.setAuthenticationFailureHandler(this);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha(), new ArrayList<>()));
		} catch (Exception e) {
			throw new JWTAuthenticationException(e.getMessage(), e);
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		System.out.println("===============================================> Authentication: " + authResult.isAuthenticated());
		UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authResult.getPrincipal();
		response.setStatus(200);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(new ObjectMapper().writeValueAsString(
			AuthenticationResponse
			.builder()
			.usuario(userDetailsImpl.getUsuario())
			.tokenAccesso(jwtService.generateAccessToken(userDetailsImpl.getUsuario()))
			.tokenAtualizacao(jwtService.generateRefreshToken(userDetailsImpl.getUsuario()))
			.build()
		));
		response.getWriter().flush();
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.getWriter().print(new ObjectMapper().writeValueAsString(
			AuthenticationFailureResponse
			.builder()
			.path(request.getRequestURI())
			.status(HttpStatus.FORBIDDEN.getReasonPhrase())
			.message(exception.getMessage())
			.build()
		));
		response.getWriter().flush();
	}
}
