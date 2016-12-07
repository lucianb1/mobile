package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.exceptions.SecurityException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.token.RememberMeToken;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.repository.RememberMeTokenRepository;
import ro.hoptrop.security.mobile.UserAuthenticationResponse;
import ro.hoptrop.service.AuthenticationService;
import ro.hoptrop.utils.TokenUtils;
import ro.hoptrop.web.MobileLoginResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private RememberMeTokenRepository tokenRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserAuthenticationResponse authenticateByToken(String token) {
		RememberMeToken rememberMeToken = null;
		try {
			rememberMeToken = tokenRepository.findToken(token);
		} catch (NotFoundException e) {
			throw new SecurityException("Token not found");
		}
		Account account = accountRepository.findAccount(rememberMeToken.getAccountID());
		return mapToAuthenticationResponse(account);
	}
	
	@Override
	public MobileLoginResponse mobileLogin(String email, String password) {
		Account account = null;
		try {
			account = accountRepository.findAccount(email);
		} catch (NotFoundException e) {
			throw new SecurityException("Invalid email or passwor");
		}
		if (passwordEncoder.matches(password, account.getPassword())) {
			String token = createTokenForAccount(account.getId());
			return mapToLoginResponse(account, token);
		} else {
			throw new SecurityException("Invalid email or password");
		}
	}
	
	@Override
	public void mobileLogout(String token) {
		tokenRepository.deleteToken(token);
	}
	
	public String createTokenForAccount(int accountID) {
		String token = TokenUtils.generateToken();
		tokenRepository.createToken(accountID, token);
		return token;
	}
	
	public UserAuthenticationResponse mapToAuthenticationResponse(Account account) {
		return new UserAuthenticationResponse()
				.setEmail(account.getEmail())
				.setAccountType(account.getType());
	}
	
	private MobileLoginResponse mapToLoginResponse(Account account, String token) {
		UserAuthenticationResponse authenticationResponse = new UserAuthenticationResponse()
				.setEmail(account.getEmail())
				.setAccountType(account.getType());
		return new MobileLoginResponse()
				.setToken(token)
				.setUser(authenticationResponse);
	}

}
