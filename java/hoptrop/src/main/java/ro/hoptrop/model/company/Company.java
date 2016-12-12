package ro.hoptrop.model.company;

/**
 * Created by Luci on 12-Dec-16.
 */
public class Company {

    private final int id;
    private final String name;
    private final Location location;

    public Company(int id, String name, Location location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }
}
