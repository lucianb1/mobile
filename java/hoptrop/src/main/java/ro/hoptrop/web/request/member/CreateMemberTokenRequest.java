package ro.hoptrop.web.request.member;

/**
 * Created by Luci on 26-Dec-16.
 */
public class CreateMemberTokenRequest {

    private int companyID;
    private String token;
    private boolean isAdmin;

    public int getCompanyID() {
        return companyID;
    }


    public String getToken() {
        return token;
    }


    public boolean isAdmin() {
        return isAdmin;
    }

    public CreateMemberTokenRequest setIsAdmin(boolean admin) {
        isAdmin = admin;
        return this;
    }

    public CreateMemberTokenRequest setCompanyID(int companyID) {
        this.companyID = companyID;
        return this;
    }

    public CreateMemberTokenRequest setToken(String token) {
        this.token = token;
        return this;
    }
}
