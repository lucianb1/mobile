package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.CompanyService;
import ro.hoptrop.web.response.company.CompanyJsonResponse;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Luci on 12-Dec-16.
 */
@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping(method = RequestMethod.GET)
    public List<CompanyJsonResponse> findCompaiesByName(@RequestParam int domainID, @RequestParam(required = false) String name) {
        return companyService.findCompaniesByNameAndDomain(domainID, name).stream().map(item -> mapToCompanyResponse(item)).collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Company getCompanyDetails(@PathVariable int id) {
        return companyService.findByID(id);
    }

    //TODO for member admin
    @RequestMapping(value = "/token/regenerate", method = RequestMethod.GET)
    public String regenerateMembersToken(PrincipalUser principal) {
        return companyService.regenerateMembersToken(principal.getCompanyID());
    }

    public CompanyJsonResponse mapToCompanyResponse(Company company) {
        return CompanyJsonResponse.builder()
                .setId(company.getId())
                .setName(company.getName())
                .setLocation(company.getLocation())
                .build();
    }

}
