package ro.hoptrop.model.token.member;

/**
 * Created by Luci on 18-Dec-16.
 */
public class MemberToken {

    private final int id;
    private final int companyID;
    private final String token;
    private final boolean isAdmin;

    MemberToken(int id, int companyID, String token, boolean isAdmin) {
        this.id = id;
        this.companyID = companyID;
        this.token = token;
        this.isAdmin = isAdmin;
    }

    public static MemberTokenBuilder builder() {
        return new MemberTokenBuilder();
    }

    public int getId() {
        return id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public String getToken() {
        return token;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}
