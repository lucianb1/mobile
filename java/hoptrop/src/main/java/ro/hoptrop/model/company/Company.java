package ro.hoptrop.model.company;

/**
 * Created by Luci on 12-Dec-16.
 */
public class Company {

    private final int id;
    private final String name;
    private final String membersToken;
    private final Location location;
    private final int orderNr;

    Company(int id, String name, String membersToken, Location location, int orderNr) {
        this.id = id;
        this.name = name;
        this.membersToken = membersToken;
        this.location = location;
        this.orderNr = orderNr;
    }

    public static CompanyBuilder builder() {
        return new CompanyBuilder();
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

    public String getMembersToken() {
        return membersToken;
    }

    public int getOrderNr() {
        return orderNr;
    }
}
