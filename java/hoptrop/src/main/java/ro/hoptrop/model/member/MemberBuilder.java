package ro.hoptrop.model.member;

public class MemberBuilder {

	private int id;
	private String accountID;
	private String companyID;
	private String name;
	private byte[] image;

	MemberBuilder() {}

	public Member build() {
		return new Member(id, accountID, companyID, name, image);
	}
	
	public MemberBuilder setId(int id) {
		this.id = id;
		return this;
	}

	public MemberBuilder setAccountID(String accountID) {
		this.accountID = accountID;
		return this;
	}

	public MemberBuilder setCompanyID(String companyID) {
		this.companyID = companyID;
		return this;
	}

	public MemberBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public MemberBuilder setImage(byte[] image) {
		this.image = image;
		return this;
	}

}
