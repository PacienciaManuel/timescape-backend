package com.timescape.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.timescape.service.JWTService;
import com.timescape.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity(debug = true)
public class JWTConfiguracao {
		
	@Bean
	@SuppressWarnings("removal")
    AuthenticationManager authenticationManager(HttpSecurity http, UserDetailsServiceImpl usuarioService, PasswordEncoder encoder) throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
	      .userDetailsService(usuarioService)
	      .passwordEncoder(encoder)
	      .and()
	      .build();
	}
	
//	@Bean
//	SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager, JWTService jwtService) throws Exception {
//		http.csrf(AbstractHttpConfigurer::disable)
//			.authorizeHttpRequests(requests -> requests
//			.requestMatchers(HttpMethod.POST, "/login").permitAll()
//			.requestMatchers(HttpMethod.POST, "/usuario").permitAll()
//			.anyRequest().authenticated()
//		).sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//		.addFilter(new AuthenticationFilter(authenticationManager))
//		.addFilter(new AuthorizationFilter(authenticationManager, jwtService));
//		return http.build();
//	}
	
	@Bean
    SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager, JWTService jwtService) throws Exception {
		http.csrf().disable().authorizeHttpRequests()
		.requestMatchers(HttpMethod.GET, "/ws").permitAll()
		.requestMatchers(HttpMethod.POST, "/login").permitAll()
		.requestMatchers(HttpMethod.POST, "/usuario").permitAll()
		.requestMatchers(HttpMethod.GET, "/video/streaming").permitAll()
		.anyRequest().authenticated()
		.and()
		.addFilter(new AuthenticationFilter(authenticationManager, jwtService))
		.addFilter(new AuthorizationFilter(authenticationManager, jwtService))
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }
	
	@Bean
	CorsConfigurationSource corsConfiguration() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
		source.registerCorsConfiguration("/**", corsConfiguration);
		return source;
	}
	
	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().requestMatchers("/upload/**");
	}
	
	@Bean
	PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
