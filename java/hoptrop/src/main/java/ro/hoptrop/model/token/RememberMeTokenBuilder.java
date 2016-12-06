package ro.hoptrop.model.token;

import java.util.Date;

public class RememberMeTokenBuilder {

	private int id;
	private int accountID;
	private String token;
	private Date creationDate;

	RememberMeTokenBuilder() {
	}

	public RememberMeToken build() {
		return new RememberMeToken(id, accountID, token, creationDate);
	}

	public RememberMeTokenBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public RememberMeTokenBuilder setAccountID(int accountID) {
		this.accountID = accountID;
		return this;
	}

	public RememberMeTokenBuilder setToken(String token) {
		this.token = token;
		return this;
	}

	public RememberMeTokenBuilder setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}

}
