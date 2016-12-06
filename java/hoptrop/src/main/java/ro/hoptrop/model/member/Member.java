package ro.hoptrop.model.member;

public class Member {

	private final int id;
	private final String accountID;
	private final String companyID;
	private final String name;
	private final byte[] image;

	Member(int id, String accountID, String companyID, String name, byte[] image) {
		this.id = id;
		this.accountID = accountID;
		this.companyID = companyID;
		this.name = name;
		this.image = image;
	}

	public static MemberBuilder builder() {
		return new MemberBuilder();
	}
	
	public MemberBuilder toBuilder() {
		return new MemberBuilder()
				.setId(id)
				.setAccountID(accountID)
				.setCompanyID(companyID)
				.setImage(image)
				.setName(name);
	}
	
	
	public int getId() {
		return id;
	}

	public String getAccountID() {
		return accountID;
	}

	public String getCompanyID() {
		return companyID;
	}

	public String getName() {
		return name;
	}

	public byte[] getImage() {
		return image;
	}

}
