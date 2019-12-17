package security.config;

import org.springframework.beans.factory.annotation.Value;

public class ZuulConfig {
	@Value("${security.zsign.header:ZUUL_ACCESS_HEADER}")
	private String header;

	@Value("${security.zsign.salt}")
	private String salt;

	@Value("${security.zsign.secret}")
	private String secret;

	public ZuulConfig() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ZuulConfig(String header, String salt, String secret) {
		super();
		this.header = header;
		this.salt = salt;
		this.secret = secret;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
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
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((salt == null) ? 0 : salt.hashCode());
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
		ZuulConfig other = (ZuulConfig) obj;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (salt == null) {
			if (other.salt != null)
				return false;
		} else if (!salt.equals(other.salt))
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
		return "ZuulConfig [header=" + header + ", salt=" + salt + ", secret=" + secret + "]";
	}

}
