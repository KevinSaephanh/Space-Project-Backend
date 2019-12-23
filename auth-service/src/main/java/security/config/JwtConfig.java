package security.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
	@Value("${security.jwt.uri:/auth/**}")
	private String uri;

	@Value("${security.jwt.header:Authorization}")
	private String header;

	@Value("${security.jwt.prefix:Bearer }")
	private String prefix;

	@Value("${security.jwt.expiration:#{2*60*60*1000}}")
	private int expiration;

	@Value("${JWT_SECRET}")
	private String secret;

	public JwtConfig() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getUri() {
		return uri;
	}

	public String getHeader() {
		return header;
	}

	public String getPrefix() {
		return prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public String getSecret() {
		return secret;
	}

}
