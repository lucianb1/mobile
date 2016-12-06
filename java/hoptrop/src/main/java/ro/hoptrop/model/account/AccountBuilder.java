package ro.hoptrop.model.account;

public class AccountBuilder {

	private int id;
	private String email;
	private String phone;
	private String name;
	private AccountType type;

	AccountBuilder() {
	}

	public Account build() {
		return new Account(id, email, phone, name, type);
	}

	public AccountBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public AccountBuilder setEmail(String email) {
		this.email = email;
		return this;
	}

	public AccountBuilder setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public AccountBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public AccountBuilder setType(AccountType type) {
		this.type = type;
		return this;
	}

}
