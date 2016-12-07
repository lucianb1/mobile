package ro.hoptrop.web;

import ro.hoptrop.security.mobile.UserAuthenticationResponse;

public class MobileLoginResponse {

	private UserAuthenticationResponse user;
	private String token;

	public UserAuthenticationResponse getUser() {
		return user;
	}

	public MobileLoginResponse setUser(UserAuthenticationResponse user) {
		this.user = user;
		return this;
	}

	public String getToken() {
		return token;
	}

	public MobileLoginResponse setToken(String token) {
		this.token = token;
		return this;
	}

}
