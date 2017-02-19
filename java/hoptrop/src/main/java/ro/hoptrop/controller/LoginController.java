package ro.hoptrop.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.security.mobile.TokenAuthentication;
import ro.hoptrop.service.AccountService;
import ro.hoptrop.service.AuthenticationService;
import ro.hoptrop.web.request.FacebookLoginRequest;
import ro.hoptrop.web.request.LoginRequest;
import ro.hoptrop.web.response.MobileLoginResponse;

import javax.validation.Valid;

@RestController
@RequestMapping("/mobile")
public class LoginController {
	private static final Logger LOG = Logger.getLogger(LoginController.class);
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value="/ping", method = RequestMethod.GET)
	public Object ping(TokenAuthentication authentication) {
		return authentication;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public MobileLoginResponse login(@Valid @RequestBody LoginRequest request) {
		return authenticationService.mobileLogin(request.getEmail(), request.getPassword());
	}
	
	@RequestMapping(value = "/secure/logout", method = RequestMethod.POST)
	public void logout(TokenAuthentication authentication) {
		SecurityContextHolder.clearContext();
		authenticationService.mobileLogout(authentication.getToken());
	}
	
	@RequestMapping(value = "/login/facebook", method = RequestMethod.POST)
	public MobileLoginResponse loginWithFacebook(@Valid @RequestBody FacebookLoginRequest request) {
		return authenticationService.loginWithFacebook(request.getToken());
	}
	
}
