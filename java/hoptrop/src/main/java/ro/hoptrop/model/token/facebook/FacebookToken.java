package ro.hoptrop.model.token.facebook;

import java.util.Date;

import ro.hoptrop.model.token.AccountToken;

public class FacebookToken extends AccountToken {

	public FacebookToken(int id, int accountID, String token, Date creationDate) {
		super(id, accountID, token, creationDate);
	}
	
	public static FacebookTokenBuilder builder() {
		return new FacebookTokenBuilder();
	}
	
}
