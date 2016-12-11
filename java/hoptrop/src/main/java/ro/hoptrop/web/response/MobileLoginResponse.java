package ro.hoptrop.web.response;

import ro.hoptrop.security.PrincipalUser;

public class MobileLoginResponse {

	private PrincipalUser user;
	private String token;

	public PrincipalUser getUser() {
		return user;
	}

	public MobileLoginResponse setUser(PrincipalUser user) {
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
