package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Service;
import ro.hoptrop.core.exceptions.AlreadyExistsException;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.model.facebook.FacebookUser;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.service.AuthenticationService;
import ro.hoptrop.service.RegistrationService;
import ro.hoptrop.utils.FacebookUtils;
import ro.hoptrop.utils.TokenUtils;
import ro.hoptrop.web.MobileLoginResponse;

@Service
public class RegistrationServiceImpl implements RegistrationService {

	private static final String NO = "NO";

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private FacebookUtils facebookUtils;

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
		String longToken = facebookUtils.createFacebookLongLivedToken(token);
		FacebookTemplate facebook = new FacebookTemplate(longToken);
		String [] fields = {"email",  "name" };
		FacebookUser loadedUser = facebook.fetchObject("me", FacebookUser.class, fields);
		String email = loadedUser.getEmail();
		String name = loadedUser.getName();
		Account account = accountRepository.createAccount(email, passwordEncoder.encode(TokenUtils.generateToken()), name, NO, AccountType.USER);
		return authenticationService.loginAccount(account);
	}

	
}
