package ro.hoptrop.service;

import ro.hoptrop.security.mobile.UserAuthenticationResponse;
import ro.hoptrop.web.MobileLoginResponse;

public interface AuthenticationService {

	MobileLoginResponse mobileLogin(String email, String password);
	
	void mobileLogout(String token);
	
	UserAuthenticationResponse authenticateByToken(String token);
	
}
