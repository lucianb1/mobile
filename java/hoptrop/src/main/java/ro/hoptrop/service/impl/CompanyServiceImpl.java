package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.repository.CompanyRepository;
import ro.hoptrop.service.CompanyService;
import ro.hoptrop.utils.TokenUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by Luci on 12-Dec-16.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public Company createCompany(String name, Location location, Set<Integer> domains, int orderNr) {
        return null;
    }

    @Override
    public void setCompanyLocation(double longitude, double latitude) {

    }

    @Override
    public void updateCompany(int id, String newName, Location newLocation, int newOrderNr) {
        companyRepository.updateCompany(id, newName, newLocation, null, newOrderNr);
    }

    @Override
    public List<Company> findCompaniesByNameAndDomain(int domainID, String name) {
        return companyRepository.findCompaniesByNameAndDomain(domainID, name == null ? null : name.trim());
    }

    @Override
    public Company findByID(int id) {
        return companyRepository.findCompany(id);
    }

    @Override
    public String regenerateMembersToken(int companyID) {
        String newToken = TokenUtils.generateMembersToken();
        while (companyRepository.membersTokenExist(newToken)) {
            newToken = TokenUtils.generateMembersToken();
        }
        companyRepository.updateCompanyMembersToken(companyID, newToken);
        return newToken;
    }

    @Override
    public String getMemberAdminToken(int companyID) {
        return companyRepository.getMemberAdminToken(companyID);
    }
}
