package com.timescape.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.timescape.exception.JWTServiceException;
import com.timescape.model.Usuario;

@Service
public class JWTService {
	private final String secret;
	private final Long expiration;
	private final Long expirationRefresh;
	public static final String PREFIX_BEARER = "Bearer ";
	public static final String HEADER_AUTHORIZATION = "authorization";

	public JWTService(@Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") Long expiration, @Value("${jwt.expiration-refresh}") Long expirationRefresh) {
		super();
		this.secret = secret;
		this.expiration = expiration;
		this.expirationRefresh = expirationRefresh;
	}	
	
	private String generateToken(Usuario usuario, Date expiresAt) {
		return PREFIX_BEARER + JWT.create()
		.withSubject(usuario.getEmail())
		.withExpiresAt(expiresAt)
		.sign(Algorithm.HMAC512(secret));
	}
	
	public String generateAccessToken(Usuario usuario) {
		return generateToken(usuario, new Date(System.currentTimeMillis() + expiration));
	}
	
	public String generateRefreshToken(Usuario usuario) {
		return generateToken(usuario, new Date(System.currentTimeMillis() + expirationRefresh));
	}
	
	public Usuario exctract(String authorization) {
		try {
			if (!authorization.startsWith(PREFIX_BEARER)) {
				throw new JWTServiceException("Autorização inválido: " + authorization);
			}
			return Usuario
			.builder()
			.email(JWT.require(Algorithm.HMAC512(secret)).build().verify(authorization.substring(7)).getSubject())
			.build();
		} catch (JWTCreationException | JWTServiceException e) {
			throw new JWTServiceException(e);
		}
	}
	
}
