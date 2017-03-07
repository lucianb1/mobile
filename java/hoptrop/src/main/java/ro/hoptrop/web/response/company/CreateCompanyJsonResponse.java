package ro.hoptrop.web.response.company;

import ro.hoptrop.model.company.Location;

/**
 * Created by Luci on 05-Mar-17.
 */
public class CreateCompanyJsonResponse {

    private String name;
    private Location location;

    private String memberToken;
    private String memberAdminToken;

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public String getMemberToken() {
        return memberToken;
    }

    public String getMemberAdminToken() {
        return memberAdminToken;
    }

    public CreateCompanyJsonResponse setName(String name) {
        this.name = name;
        return this;
    }

    public CreateCompanyJsonResponse setLocation(Location location) {
        this.location = location;
        return this;
    }

    public CreateCompanyJsonResponse setMemberToken(String memberToken) {
        this.memberToken = memberToken;
        return this;
    }

    public CreateCompanyJsonResponse setMemberAdminToken(String memberAdminToken) {
        this.memberAdminToken = memberAdminToken;
        return this;
    }
}
