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
}
