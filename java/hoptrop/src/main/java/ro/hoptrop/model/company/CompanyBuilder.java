package ro.hoptrop.model.company;

/**
 * Created by Luci on 17-Dec-16.
 */
public class CompanyBuilder {

    private int id;
    private String name;
    private String membersToken;
    private Location location;
    private int orderNr;

    CompanyBuilder() {
    }

    public Company build() {
        return new Company(id, name, membersToken, location, orderNr);
    }

    public CompanyBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CompanyBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyBuilder setMembersToken(String membersToken) {
        this.membersToken = membersToken;
        return this;
    }

    public CompanyBuilder setLocation(Location location) {
        this.location = location;
        return this;
    }

    public CompanyBuilder setOrderNr(int orderNr) {
        this.orderNr = orderNr;
        return this;
    }
}
