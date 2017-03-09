package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.model.domain.CompanyDomain;
import ro.hoptrop.service.DomainService;
import ro.hoptrop.web.response.domain.DomainsJsonResponse;

import java.util.List;

/**
 * Created by Luci on 12-Dec-16.
 */
@RestController
public class DomainController {

    @Autowired
    private DomainService domainService;


    @RequestMapping(value="/domain/companies", method = RequestMethod.GET)
    public DomainsJsonResponse getCompanyDomains() {
        List<CompanyDomain> domains = domainService.getAllCompanyDomains();
        return new DomainsJsonResponse().setDomains(domains);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @RequestMapping(value = "/secure/domain/companies/{name}/{order}", method = RequestMethod.POST)
    public void createCompanyDomain(@PathVariable String name, @PathVariable int order) {
        domainService.createCompanyDomain(name, order);
    }

}
