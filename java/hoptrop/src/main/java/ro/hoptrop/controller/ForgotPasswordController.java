package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.service.ForgotPasswordService;
import ro.hoptrop.web.request.ResetPasswordRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/mobile")
public class ForgotPasswordController {

	@Autowired
	private ForgotPasswordService service;
	
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public void resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
		service.resetPassword(request.getEmail());
	}
	
}
