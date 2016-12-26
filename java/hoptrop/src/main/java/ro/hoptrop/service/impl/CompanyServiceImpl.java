package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.model.token.member.MemberToken;
import ro.hoptrop.repository.CompanyRepository;
import ro.hoptrop.repository.MemberTokenRepository;
import ro.hoptrop.service.CompanyService;
import ro.hoptrop.utils.TokenUtils;
import ro.hoptrop.web.request.member.CreateMemberTokenRequest;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Luci on 12-Dec-16.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private MemberTokenRepository memberTokenRepository;

    @Override
    public Company createCompany(String name, Location location, Set<Integer> domains, int orderNr) {
        //TODO check unique name
        Company company = companyRepository.createCompany(name, location, orderNr);
        memberTokenRepository.createMemberTokens(Arrays.asList(
                new CreateMemberTokenRequest().setCompanyID(company.getId()).setToken(TokenUtils.generateMembersToken()).setIsAdmin(false), // members
                new CreateMemberTokenRequest().setCompanyID(company.getId()).setToken(TokenUtils.generateMembersToken()).setIsAdmin(true) // admin
        ));
        //TODO validate domains actually exists
        companyRepository.setCompanyDomains(company.getId(), domains);
        return company;
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
    public MemberToken getMemberAdminToken(int companyID) {
        return memberTokenRepository.getMemberAdminTokenForCompany(companyID);
    }

    @Override
    public MemberToken getMembersToken(int companyID) {
        return memberTokenRepository.getMembersTokenForCompany(companyID);
    }
}
