package ro.hoptrop.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ro.hoptrop.service.RegistrationService;
import ro.hoptrop.web.MobileLoginResponse;
import ro.hoptrop.web.RegisterRequest;

@RestController
@RequestMapping("/mobile/register")
public class RegistrationController {

	@Autowired
	private RegistrationService registrationService;
	
	@RequestMapping( method = RequestMethod.POST)
	public MobileLoginResponse register(@Valid @RequestBody RegisterRequest request) {
		return registrationService.registerAccount(request.getEmail(), request.getPassword(), request.getName(), request.getPhone());
	}
	
}
