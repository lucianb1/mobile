package ro.hoptrop.model.token;

import java.util.Date;

public class AccountToken {

	private final int id;
	private final int accountID;
	private final String token;
	private final Date creationDate;

	public AccountToken(int id, int accountID, String token, Date creationDate) {
		this.id = id;
		this.accountID = accountID;
		this.token = token;
		this.creationDate = creationDate;
	}

	public int getId() {
		return id;
	}

	public int getAccountID() {
		return accountID;
	}

	public String getToken() {
		return token;
	}

	public Date getCreationDate() {
		return creationDate;
	}

}
