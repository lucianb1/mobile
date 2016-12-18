package ro.hoptrop.web.response.company;

import ro.hoptrop.model.company.Location;

/**
 * Created by Luci on 12-Dec-16.
 */
public class CompanyJsonResponseBuilder {

    private int id;
    private String name;
    private Location location;

    CompanyJsonResponseBuilder() {}

    public CompanyJsonResponse build() {
        return new CompanyJsonResponse(id, name, location);
    }

    public CompanyJsonResponseBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CompanyJsonResponseBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyJsonResponseBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }
}
