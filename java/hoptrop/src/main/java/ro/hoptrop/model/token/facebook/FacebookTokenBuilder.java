package ro.hoptrop.model.token.facebook;

import java.util.Date;

public class FacebookTokenBuilder {

	private int id;
	private int accountID;
	private String token;
	private Date creationDate;

	FacebookTokenBuilder() {
	}

	public FacebookToken build() {
		return new FacebookToken(id, accountID, token, creationDate);
	}

	public FacebookTokenBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public FacebookTokenBuilder setAccountID(int accountID) {
		this.accountID = accountID;
		return this;
	}

	public FacebookTokenBuilder setToken(String token) {
		this.token = token;
		return this;
	}

	public FacebookTokenBuilder setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
		return this;
	}
	
}
