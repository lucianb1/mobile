package ro.hoptrop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.exceptions.SecurityException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.token.RememberMeToken;
import ro.hoptrop.repository.AccountRepository;
import ro.hoptrop.repository.RememberMeTokenRepository;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.AuthenticationService;
import ro.hoptrop.utils.TokenUtils;
import ro.hoptrop.web.MobileLoginResponse;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private static final Logger LOG = Logger.getLogger(AuthenticationServiceImpl.class);
	
	@Autowired
	private RememberMeTokenRepository tokenRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AccountRepository accountRepository;

	@Override
	public PrincipalUser authenticateByToken(String token) {
		RememberMeToken rememberMeToken = null;
		try {
			rememberMeToken = tokenRepository.findToken(token);
		} catch (NotFoundException e) {
			throw new SecurityException("Token not found");
		}
		Account account = accountRepository.findAccount(rememberMeToken.getAccountID());
		return mapToPrincipal(account);
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
			LOG.info("Successfully login for user with email: " + email);
			return mapToLoginResponse(account, token);
		} else {
			throw new SecurityException("Invalid email or password");
		}
	}
	
	@Override
	public void mobileLogout(String token) {
		tokenRepository.deleteToken(token);
	}
	
	@Override
	public MobileLoginResponse loginAccount(Account account) {
		String token = TokenUtils.generateToken();
		while (tokenRepository.tokenExists(token)) {
			token = TokenUtils.generateToken();
		}
		tokenRepository.createToken(account.getId(), token);
		return mapToLoginResponse(account, token);
	}
	
	public String createTokenForAccount(int accountID) {
		String token = TokenUtils.generateToken();
		tokenRepository.createToken(accountID, token);
		return token;
	}
	
	public PrincipalUser mapToPrincipal(Account account) {
		return new PrincipalUser(account.getEmail(), "", createAuthorities(account.getType().name()))
				.setName(account.getName())
				.setPhone(account.getPhone());
	}
	
	private MobileLoginResponse mapToLoginResponse(Account account, String token) {
		return new MobileLoginResponse()
				.setToken(token)
				.setUser(mapToPrincipal(account));
	}
	
	private List<GrantedAuthority> createAuthorities(String role) {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(role));
		return authorities;
	}

	private String createFacebookLongLivedToken(String token, String appID, String secretID) {
		RestTemplate restTemplate = new RestTemplate();
		return null;
	}

}
