package security.filters;

import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import models.AppUser;
import security.config.JwtConfig;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;

	private JwtConfig jwtConfig;

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;

		// Override default path to listen for "/auth" instead of "/login"
		this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(jwtConfig.getUri(), "POST"));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			// Retrieve user credentials from the request
			AppUser creds = new ObjectMapper().readValue(req.getInputStream(), AppUser.class);

			// Create an auth object containing the credentials to be used by the
			// authentication manager
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), Collections.emptyList());

			// Authenticate the user
			return authenticationManager.authenticate(authToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		Long now = System.currentTimeMillis();
		String token = Jwts.builder().setSubject(auth.getName())
				// Convert to list of strings
				.claim("authorities",
						auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(now)).setExpiration(new Date(now + jwtConfig.getExpiration()))
				.signWith(SignatureAlgorithm.HS512, jwtConfig.getSecret().getBytes()).compact();

		// Add token to header
		res.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
	}
}
