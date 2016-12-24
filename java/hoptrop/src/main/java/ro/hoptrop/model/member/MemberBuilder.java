package ro.hoptrop.model.member;

public class MemberBuilder {

	private int id;
	private int accountID;
	private int companyID;
	private String name;
	private boolean isActive;

	MemberBuilder() {}

	public Member build() {
		return new Member(id, accountID, companyID, name, isActive);
	}
	
	public MemberBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public MemberBuilder setAccountID(int accountID) {
		this.accountID = accountID;
		return this;
	}

	public MemberBuilder setCompanyID(int companyID) {
		this.companyID = companyID;
		return this;
	}

	public MemberBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public MemberBuilder setIsActive(boolean active) {
		isActive = active;
		return this;
	}
}
