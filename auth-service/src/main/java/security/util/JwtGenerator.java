package security.util;

import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import security.config.JwtConfig;

public class JwtGenerator {
	public static String generateToken(Authentication auth, JwtConfig jwtConfig) {
		SignatureAlgorithm sigAlg = SignatureAlgorithm.HS512;
		long nowMillis = System.currentTimeMillis();

		JwtBuilder builder = Jwts.builder().setSubject(auth.getName()).setIssuer("astro")
				.claim("authorities",
						auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(nowMillis)).setExpiration(new Date(nowMillis + jwtConfig.getExpiration()))
				.signWith(sigAlg, jwtConfig.getSecret().getBytes());
		return builder.compact();
	}
}
