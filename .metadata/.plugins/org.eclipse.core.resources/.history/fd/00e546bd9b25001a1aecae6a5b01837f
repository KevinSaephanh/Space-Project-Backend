package security.config;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
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

	public JwtConfig(String header, String prefix, int expiration, String secret) {
		super();
		this.header = header;
		this.prefix = prefix;
		this.expiration = expiration;
		this.secret = secret;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getExpiration() {
		return expiration;
	}

	public void setExpiration(int expiration) {
		this.expiration = expiration;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + expiration;
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		result = prime * result + ((secret == null) ? 0 : secret.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JwtConfig other = (JwtConfig) obj;
		if (expiration != other.expiration)
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		if (secret == null) {
			if (other.secret != null)
				return false;
		} else if (!secret.equals(other.secret))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JwtConfig [header=" + header + ", prefix=" + prefix + ", expiration=" + expiration + ", secret="
				+ secret + "]";
	}

}
