package security.filters;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import models.AppUser;
import security.config.JwtConfig;
import security.util.JwtGenerator;

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

			// Create an auth object containing the credentials
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getUsername(),
					creds.getPassword(), Collections.emptyList());

			// Authenticate the user using auth token
			return authenticationManager.authenticate(authToken);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		String token = JwtGenerator.generateToken(auth, jwtConfig);

		// Add token to header
		res.addHeader(jwtConfig.getHeader(), jwtConfig.getPrefix() + token);
	}
}
