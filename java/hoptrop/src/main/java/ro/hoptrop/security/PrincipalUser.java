package ro.hoptrop.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class PrincipalUser extends User {

	private static final long serialVersionUID = -7022660152199683334L;

	public PrincipalUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}

}
