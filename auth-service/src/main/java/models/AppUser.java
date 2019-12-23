package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
public class AppUser implements Serializable {
	private static final long serialVersionUID = -3617614278761535312L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank
	@Column(nullable = false, length = 25, unique = true)
	private String username;

	@Email
	@NotBlank
	@Column(nullable = false, length = 50, unique = true)
	private String email;

	@NotBlank
	@Column(nullable = false, length = 100)
	private String password;

	@NotBlank
	@Column(nullable = false, length = 10)
	private String role;

	@Column(nullable = true, name = "profile_pic")
	private String profilePic;

	@Column(nullable = true, name = "description")
	private String description;

	@Column(nullable = true, name = "twitter")
	private String twitter;

	@Column(nullable = true, name = "facebook")
	private String facebook;

	@Column(nullable = true, name = "linkedin")
	private String linkedIn;

	public AppUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AppUser(int id, @NotBlank String username, @Email @NotBlank String email, @NotBlank String password,
			@NotBlank String role, String profilePic, String description, String twitter, String facebook,
			String linkedIn) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.role = role;
		this.profilePic = profilePic;
		this.description = description;
		this.twitter = twitter;
		this.facebook = facebook;
		this.linkedIn = linkedIn;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getLinkedIn() {
		return linkedIn;
	}

	public void setLinkedIn(String linkedIn) {
		this.linkedIn = linkedIn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((facebook == null) ? 0 : facebook.hashCode());
		result = prime * result + id;
		result = prime * result + ((linkedIn == null) ? 0 : linkedIn.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((profilePic == null) ? 0 : profilePic.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result + ((twitter == null) ? 0 : twitter.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		AppUser other = (AppUser) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (facebook == null) {
			if (other.facebook != null)
				return false;
		} else if (!facebook.equals(other.facebook))
			return false;
		if (id != other.id)
			return false;
		if (linkedIn == null) {
			if (other.linkedIn != null)
				return false;
		} else if (!linkedIn.equals(other.linkedIn))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (profilePic == null) {
			if (other.profilePic != null)
				return false;
		} else if (!profilePic.equals(other.profilePic))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (twitter == null) {
			if (other.twitter != null)
				return false;
		} else if (!twitter.equals(other.twitter))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password + ", role="
				+ role + ", profilePic=" + profilePic + ", description=" + description + ", twitter=" + twitter
				+ ", facebook=" + facebook + ", linkedIn=" + linkedIn + "]";
	}

}
