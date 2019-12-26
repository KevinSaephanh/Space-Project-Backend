package com.filters;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.config.JwtConfig;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtConfig jwtConfig;

	public JwtAuthenticationFilter(JwtConfig jwtConfig) {
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// Get the authentication header containing the token
		String header = request.getHeader(jwtConfig.getHeader());

		// validate the header and check the prefix
		if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
			chain.doFilter(request, response); 
			return;
		}

		// Get the token
		String token = header.replace(jwtConfig.getPrefix(), "");

		try {
			// Validate the token
			Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token)
					.getBody();

			String username = claims.getSubject();
			if (username != null) {
				@SuppressWarnings("unchecked")
				List<String> authorities = (List<String>) claims.get("authorities");

				// Create auth object
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

				// Authenticate the user
				SecurityContextHolder.getContext().setAuthentication(auth);
			}

		} catch (Exception e) {
			// Make sure it's clear; so guarantee user won't be authenticated
			SecurityContextHolder.clearContext();
		}

		// Go to the next filter in the filter chain
		chain.doFilter(request, response);
	}

}
