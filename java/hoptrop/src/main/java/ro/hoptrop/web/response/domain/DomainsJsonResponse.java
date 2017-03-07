package ro.hoptrop.web.response.domain;

import ro.hoptrop.model.domain.CompanyDomain;

import java.util.List;

/**
 * Created by Luci on 06-Mar-17.
 */
public class DomainsJsonResponse {

    private List<CompanyDomain> domains;

    public List<CompanyDomain> getDomains() {
        return domains;
    }

    public DomainsJsonResponse setDomains(List<CompanyDomain> domains) {
        this.domains = domains;
        return this;
    }
}
