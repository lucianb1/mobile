package ro.hoptrop.web;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

import ro.hoptrop.core.validator.ValidPassword;

public class LoginRequest {

	@NotNull
	@Email
	private String email;
	
	@ValidPassword
	private String password;

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

}
