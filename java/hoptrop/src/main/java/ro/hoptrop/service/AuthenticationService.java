package ro.hoptrop.service;

import ro.hoptrop.model.account.Account;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.web.response.MobileLoginResponse;

public interface AuthenticationService {

	MobileLoginResponse mobileLogin(String email, String password);
	
	void mobileLogout(String token);
	
	PrincipalUser authenticateByToken(String token);
	
	MobileLoginResponse loginAccount(Account account);

    MobileLoginResponse loginWithFacebook(String token);
}
