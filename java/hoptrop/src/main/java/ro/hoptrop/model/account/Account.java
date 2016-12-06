package ro.hoptrop.model.account;

public class Account {

	private final int id;
	private final String email;
	private final String phone;
	private final String name;
	private final AccountType type;
	// private final boolean isFacebook;

	Account(int id, String email, String phone, String name, AccountType type) {
		this.id = id;
		this.email = email;
		this.phone = phone;
		this.name = name;
		this.type = type;
	}
	
	public static AccountBuilder builder() {
		return new AccountBuilder();
	}
	
	public AccountBuilder toBuilder() {
		return new AccountBuilder()
				.setEmail(email)
				.setId(id)
				.setName(name)
				.setPhone(phone)
				.setType(type);
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public String getName() {
		return name;
	}

	public AccountType getType() {
		return type;
	}

}
