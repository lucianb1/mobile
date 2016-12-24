package ro.hoptrop.web.request.member;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Luci on 24-Dec-16.
 */
public class CreateMemberServiceRequest {

    @NotNull
    @Size(min = 3, max = 150)
    private String name;

    @NotNull
    private Integer orderNr;

    @NotNull
    private Integer domainID;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNr() {
        return orderNr;
    }

    public void setOrderNr(Integer orderNr) {
        this.orderNr = orderNr;
    }

    public Integer getDomainID() {
        return domainID;
    }

    public void setDomainID(Integer domainID) {
        this.domainID = domainID;
    }
}
