package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.CompanyService;
import ro.hoptrop.web.request.company.CreateCompanyRequest;
import ro.hoptrop.web.response.company.CompanyJsonResponse;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Luci on 12-Dec-16.
 */
@RestController
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    //TODO admin role
    @RequestMapping(value = "/secure/companies", method = RequestMethod.POST)
    public void createCompany(@Valid @RequestBody CreateCompanyRequest request, @AuthenticationPrincipal PrincipalUser principal) {
        companyService.createCompany(request.getName(), request.getLocation(), request.getDomains(), 0); //TODO
    }

    @RequestMapping(value ="/companies", method = RequestMethod.GET)
    public List<CompanyJsonResponse> findCompaiesByName(@RequestParam int domainID, @RequestParam(required = false) String name) {
        return companyService.findCompaniesByNameAndDomain(domainID, name).stream().map(this::mapToCompanyResponse).collect(Collectors.toList());
    }

    @RequestMapping(value = "/companies/{id}", method = RequestMethod.GET)
    public Company getCompanyDetails(@PathVariable int id) {
        return companyService.findByID(id);
    }

    //TODO for member admin
    @RequestMapping(value = "/secure/companies/token/regenerate", method = RequestMethod.GET)
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
