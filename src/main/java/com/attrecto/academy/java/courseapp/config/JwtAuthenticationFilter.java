package com.attrecto.academy.java.courseapp.config;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.attrecto.academy.java.courseapp.model.User;
import com.attrecto.academy.java.courseapp.persistence.UserRepository;
import com.attrecto.academy.java.courseapp.security.JwtAuthentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private UserRepository userRepository;

	public static final String SECRET_KEY = "s3cr3tcouprseappkeys3cr3tcouprseappkey";
	public static final String AUDIENCE = "CourseApp";
	public static final String ISSUER = "Attrecto";
	public static final int MAX_TIME_TO_LIVE_SEC = 3600;
	private static final HmacKey TOKEN_SECRET_KEY = new HmacKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
	public static final String[] EXCLUDED_ENDPOINTS = new String[] {"/account/login", "/h2-console/**", "/openapi-ui/**", "/swagger-ui/**", "/v3/**"};

	
	public JwtAuthenticationFilter(final UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if(request.getMethod().equals("OPTIONS")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		final String authorizationHeaderValue = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (authorizationHeaderValue == null
				|| !StringUtils.startsWithIgnoreCase(authorizationHeaderValue, "Bearer ")) {
			throw new RuntimeException("Authorization token is missing!");
		}

		final JwtConsumer jwtConsumer = new JwtConsumerBuilder().setVerificationKey(TOKEN_SECRET_KEY)
				.setExpectedAudience(AUDIENCE).setExpectedIssuer(ISSUER).build();
		try {
			String substring = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
			final JwtClaims jwtClaims = jwtConsumer.processToClaims(substring);

			final String subject = jwtClaims.getSubject();
			Optional<User> maybeUser = userRepository.findByName(subject);
			if (maybeUser.isEmpty()) {
				throw new NotFoundException();
			}

			SecurityContextHolder.getContext().setAuthentication(
					new JwtAuthentication(subject, Collections.singletonList(map(maybeUser.get().getRole().name()))));
		} catch (InvalidJwtException | MalformedClaimException | NotFoundException ex) {
			throw new RuntimeException(ex);
		}
		
		response.addHeader("Access-Control-Allow-Methods:", "GET, OPTIONS, PUT, POST, DELETE");
		
		filterChain.doFilter(request, response);
	}
	
	

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
	    return Arrays.stream(EXCLUDED_ENDPOINTS)
	            .anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));
	}

	private GrantedAuthority map(final String role) {
		return new SimpleGrantedAuthority("ROLE_" + role);
	}
}
