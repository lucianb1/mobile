package ro.hoptrop.model.member;

public class MemberBuilder {

	private int id;
	private int accountID;
	private int companyID;
	private String name;

	MemberBuilder() {}

	public Member build() {
		return new Member(id, accountID, companyID, name);
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

}
