package ro.hoptrop.model.company;

/**
 * Created by Luci on 17-Dec-16.
 */
public class CompanyBuilder {

    private int id;
    private String name;
    private Location location;
    private int orderNr;
    private String memberToken;
    private String memberAdminToken;

    CompanyBuilder() {
    }

    public Company build() {
        return new Company(id, name, location, orderNr, memberToken, memberAdminToken);
    }

    public CompanyBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CompanyBuilder setName(String name) {
        this.name = name;
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

    public CompanyBuilder setMemberToken(String memberToken) {
        this.memberToken = memberToken;
        return this;
    }

    public CompanyBuilder setMemberAdminToken(String memberAdminToken) {
        this.memberAdminToken = memberAdminToken;
        return this;
    }
}
