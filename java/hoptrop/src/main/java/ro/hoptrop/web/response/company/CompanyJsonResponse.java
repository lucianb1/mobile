package ro.hoptrop.web.response.company;

import ro.hoptrop.model.company.Location;

/**
 * Created by Luci on 12-Dec-16.
 */
public class CompanyJsonResponse {

    private final int id;
    private final String name;
    private final Location location;

    CompanyJsonResponse(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }

    public static CompanyJsonResponseBuilder builder() {
        return new CompanyJsonResponseBuilder();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }
}
