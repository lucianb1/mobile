package ro.hoptrop.model.token;

import java.util.Date;

public class RememberMeToken extends AccountToken {

	public RememberMeToken(int id, int accountID, String token, Date creationDate) {
		super(id, accountID, token, creationDate);
	}
	
	public static RememberMeTokenBuilder builder() {
		return new RememberMeTokenBuilder();
	}
}
