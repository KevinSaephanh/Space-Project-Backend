package security.filters;

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

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import security.config.JwtConfig;

public class TokenFilter extends OncePerRequestFilter {
	private final JwtConfig jwtConfig;

	public TokenFilter(JwtConfig jwtConfig) {
		super();
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String header = request.getHeader(jwtConfig.getHeader());

		// Check if header contains appropriate value
		if (header == null || !header.startsWith(jwtConfig.getPrefix())) {
			filterChain.doFilter(request, response);
			return;
		}

		// Extract token from header
		String token = header.replaceAll(jwtConfig.getPrefix(), "");

		try {
			// Parse claims from token
			Claims claims = Jwts.parser().setSigningKey(jwtConfig.getSecret().getBytes()).parseClaimsJws(token)
					.getBody();

			// Get username from claims
			String username = claims.getSubject();
			if (username != null) {
				@SuppressWarnings("unchecked")
				List<String> authoritiesClaim = (List<String>) claims.get("authorities");

				List<SimpleGrantedAuthority> grantedAuthorities = authoritiesClaim.stream()
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());

				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null,
						grantedAuthorities);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		} catch (Exception e) {
			SecurityContextHolder.clearContext();
		}

		filterChain.doFilter(request, response);
	}
}
