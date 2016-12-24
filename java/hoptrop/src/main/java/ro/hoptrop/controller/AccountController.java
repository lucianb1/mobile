package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.core.exceptions.AlreadyExistsException;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.AccountService;
import ro.hoptrop.service.AuthenticationService;
import ro.hoptrop.web.request.RegisterRequest;
import ro.hoptrop.web.request.UpdateAccountRequest;
import ro.hoptrop.web.response.MobileLoginResponse;

import javax.validation.Valid;

/**
 * Created by Luci on 11-Dec-16.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationService authenticationService;

    @RequestMapping(method = RequestMethod.PUT)
    public void updateAccount(@Valid @RequestBody UpdateAccountRequest request, @AuthenticationPrincipal PrincipalUser principal) {
        accountService.updateAccountDetails(principal.getId(), request.getName(), request.getPhone());
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public MobileLoginResponse register(@Valid @RequestBody RegisterRequest request) throws AlreadyExistsException {
        Account registeredAccount = accountService.registerUser(request.getEmail(), request.getPassword(), request.getName(), request.getPhone(), AccountType.USER);
        return authenticationService.loginAccount(registeredAccount);
    }

}
