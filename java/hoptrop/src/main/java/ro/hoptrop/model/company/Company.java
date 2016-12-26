package ro.hoptrop.model.company;

/**
 * Created by Luci on 12-Dec-16.
 */
public class Company {

    private final int id;
    private final String name;
    private final Location location;
    private final int orderNr;

    Company(int id, String name, Location location, int orderNr) {
        this.id = id;
        this.name = name;
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


    public int getOrderNr() {
        return orderNr;
    }
}
