package cz.muni.fi.pa165.sportsClub.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AuthenticationDto {

	@NotNull
	@Pattern(regexp = "[^@]+@[^@]+\\.[^@]+")
	private String email;

	@NotNull
	@Size(min = 8, max = 64)
	private String password;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof AuthenticationDto))
			return false;
		AuthenticationDto other = (AuthenticationDto) obj;
		if (getEmail() == null) {
			if (other.getEmail() != null)
				return false;
		} else if (!getEmail().equals(other.getEmail()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ManagerAuthentication [email=" + email + ", password=" + password + "]";
	}

}
