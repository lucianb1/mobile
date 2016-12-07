package ro.hoptrop.security.mobile;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import ro.hoptrop.security.PrincipalUser;

public class TokenAuthentication implements Authentication {

	private static final long serialVersionUID = 423L;

	private PrincipalUser principal;
	private boolean isAuthenticated;
	private String token;

	@Override
	public String getName() {
		return principal.getUsername();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return principal.getAuthorities();
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return principal;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setPrincipal(PrincipalUser principal) {
		this.principal = principal;
	}

}
