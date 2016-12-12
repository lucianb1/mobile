package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.model.domain.CompanyDomain;
import ro.hoptrop.service.DomainService;

import java.util.List;

/**
 * Created by Luci on 12-Dec-16.
 */
@RestController
@RequestMapping("/domains")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @RequestMapping(value="/company", method = RequestMethod.GET)
    public List<CompanyDomain> getCompanyDomains() {
        return domainService.getAllCompanyDomains();
    }

}
