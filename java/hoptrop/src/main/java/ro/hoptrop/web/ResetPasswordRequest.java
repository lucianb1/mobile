package ro.hoptrop.web;

import org.hibernate.validator.constraints.Email;

public class ResetPasswordRequest {

	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
