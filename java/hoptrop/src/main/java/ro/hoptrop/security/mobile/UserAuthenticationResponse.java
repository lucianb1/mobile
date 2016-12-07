package ro.hoptrop.security.mobile;

import ro.hoptrop.model.account.AccountType;

public class UserAuthenticationResponse {

	private String email;
	private AccountType accountType;

	public String getEmail() {
		return email;
	}

	public UserAuthenticationResponse setEmail(String email) {
		this.email = email;
		return this;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public UserAuthenticationResponse setAccountType(AccountType accountType) {
		this.accountType = accountType;
		return this;
	}
}
