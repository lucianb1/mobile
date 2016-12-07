package ro.hoptrop.service;

import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.web.MobileLoginResponse;

public interface AuthenticationService {

	MobileLoginResponse mobileLogin(String email, String password);
	
	void mobileLogout(String token);
	
	PrincipalUser authenticateByToken(String token);
	
}
