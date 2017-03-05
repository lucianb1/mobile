package ro.hoptrop.model.member;

public class Member {

    private final int id;
    private final int accountID;
    private final int companyID;
    private final String name;
    private final MemberStatus status;

    Member(int id, int accountID, int companyID, String name, MemberStatus status) {
        this.id = id;
        this.accountID = accountID;
        this.companyID = companyID;
        this.name = name;
        this.status = status;
    }

    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    public MemberBuilder toBuilder() {
        return new MemberBuilder()
                .setId(id)
                .setAccountID(accountID)
                .setCompanyID(companyID)
                .setName(name);
    }


    public int getId() {
        return id;
    }

    public int getAccountID() {
        return accountID;
    }

    public int getCompanyID() {
        return companyID;
    }

    public String getName() {
        return name;
    }

    public MemberStatus getStatus() {
        return status;
    }
}
