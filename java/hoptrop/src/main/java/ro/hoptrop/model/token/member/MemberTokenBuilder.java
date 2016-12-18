package ro.hoptrop.model.token.member;

/**
 * Created by Luci on 18-Dec-16.
 */
public class MemberTokenBuilder {

    private int id;
    private int companyID;
    private String token;
    private boolean isAdmin;

    MemberTokenBuilder() {}

    public MemberToken build() {
        return new MemberToken(id, companyID, token, isAdmin);
    }

    public MemberTokenBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public MemberTokenBuilder setCompanyID(int companyID) {
        this.companyID = companyID;
        return this;
    }

    public MemberTokenBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public MemberTokenBuilder setIsAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }
}


