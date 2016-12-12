package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.CompanyService;

/**
 * Created by Luci on 12-Dec-16.
 */
@RestController
@RequestMapping("companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    public void findCompaiesByName(@RequestParam(required = false) String name) {

    }

    public void getCompanyDetails(int id) {

    }

    public void updateCompany() {

    }

    @RequestMapping(value="/token/generate", method = RequestMethod.GET)
    public String regenerateMembersToken(PrincipalUser principal) {
        return null;
    }

}
