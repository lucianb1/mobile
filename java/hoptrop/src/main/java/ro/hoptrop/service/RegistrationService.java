package ro.hoptrop.service;

import ro.hoptrop.web.MobileLoginResponse;

public interface RegistrationService {

	MobileLoginResponse registerAccount(String email, String password, String name, String phone);
	
	public MobileLoginResponse registerFacebookAccount(String token);
	
}
