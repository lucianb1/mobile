package ro.hoptrop.model.domain;

/**
 * Created by Luci on 12-Dec-16.
 */
public class CompanyDomainBuilder {

    private int id;
    private String name;
    private int orderNr;

    CompanyDomainBuilder() {
    }

    public CompanyDomain build() {
        return new CompanyDomain(id, name, orderNr);
    }

    public CompanyDomainBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public CompanyDomainBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyDomainBuilder setOrderNr(int orderNr) {
        this.orderNr = orderNr;
        return this;
    }
}
