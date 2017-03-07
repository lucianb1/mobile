package ro.hoptrop.model.domain;

/**
 * Created by Luci on 12-Dec-16.
 */
public class CompanyDomain {

    private int id;
    private String name;
    private int orderNr;

    public CompanyDomain() {
    }

    CompanyDomain(int id, String name, int orderNr) {
        this.id = id;
        this.name = name;
        this.orderNr = orderNr;
    }

    public static CompanyDomainBuilder builder() {
        return new CompanyDomainBuilder();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getOrderNr() {
        return orderNr;
    }

}
