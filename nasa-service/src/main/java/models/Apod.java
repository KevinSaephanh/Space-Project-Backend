package models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Apod {
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private String copyright;
	private Date date;
	private String explanation;
	private String hdurl;
	private String media_type;
	private String service_version;
	private String title;
	private String url;

	public Apod() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Apod(String copyright, Date date, String explanation, String hdurl, String media_type,
			String service_version, String title, String url) {
		super();
		this.copyright = copyright;
		this.date = date;
		this.explanation = explanation;
		this.hdurl = hdurl;
		this.media_type = media_type;
		this.service_version = service_version;
		this.title = title;
		this.url = url;
	}

	public SimpleDateFormat getFormat() {
		return format;
	}

	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getHdurl() {
		return hdurl;
	}

	public void setHdurl(String hdurl) {
		this.hdurl = hdurl;
	}

	public String getMedia_type() {
		return media_type;
	}

	public void setMedia_type(String media_type) {
		this.media_type = media_type;
	}

	public String getService_version() {
		return service_version;
	}

	public void setService_version(String service_version) {
		this.service_version = service_version;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((copyright == null) ? 0 : copyright.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((explanation == null) ? 0 : explanation.hashCode());
		result = prime * result + ((format == null) ? 0 : format.hashCode());
		result = prime * result + ((hdurl == null) ? 0 : hdurl.hashCode());
		result = prime * result + ((media_type == null) ? 0 : media_type.hashCode());
		result = prime * result + ((service_version == null) ? 0 : service_version.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
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
		Apod other = (Apod) obj;
		if (copyright == null) {
			if (other.copyright != null)
				return false;
		} else if (!copyright.equals(other.copyright))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (explanation == null) {
			if (other.explanation != null)
				return false;
		} else if (!explanation.equals(other.explanation))
			return false;
		if (format == null) {
			if (other.format != null)
				return false;
		} else if (!format.equals(other.format))
			return false;
		if (hdurl == null) {
			if (other.hdurl != null)
				return false;
		} else if (!hdurl.equals(other.hdurl))
			return false;
		if (media_type == null) {
			if (other.media_type != null)
				return false;
		} else if (!media_type.equals(other.media_type))
			return false;
		if (service_version == null) {
			if (other.service_version != null)
				return false;
		} else if (!service_version.equals(other.service_version))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Apod [format=" + format + ", copyright=" + copyright + ", date=" + date + ", explanation=" + explanation
				+ ", hdurl=" + hdurl + ", media_type=" + media_type + ", service_version=" + service_version
				+ ", title=" + title + ", url=" + url + "]";
	}

}
