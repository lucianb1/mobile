package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;

import ro.hoptrop.core.exceptions.AlreadyExistsException;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.service.AuthenticationService;
import ro.hoptrop.service.RegistrationService;
import ro.hoptrop.web.MobileLoginResponse;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public MobileLoginResponse registerAccount(String email, String password, String name, String phone) {
		try {
			accountRepository.findAccount(email);
			throw new AlreadyExistsException("Email already used");
		} catch (NotFoundException e) { // the email is not used yet
			Account account = accountRepository.createAccount(email, passwordEncoder.encode(password), name, phone, AccountType.USER);
			return authenticationService.loginAccount(account);
		}
	}

	@Override
	public MobileLoginResponse registerFacebookAccount(String token) {
		FacebookTemplate facebook = new FacebookTemplate(token);
		return null;
	}

}
