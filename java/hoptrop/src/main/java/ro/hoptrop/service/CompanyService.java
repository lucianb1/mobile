package ro.hoptrop.service;

import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.model.token.member.MemberToken;

import java.util.List;
import java.util.Set;

/**
 * Created by Luci on 12-Dec-16.
 */
public interface CompanyService {

    Company createCompany(String name, Location location, Set<Integer> domains, int orderNr);

    void setCompanyLocation(double longitude, double latitude);

    void updateCompany(int id, String newName, Location newLocation, int newOrderNr);

    List<Company> findCompaniesByNameAndDomain(int domainID, String name);

    Company findByID(int id);

    String regenerateMembersToken(int companyID);

    MemberToken getMemberAdminToken(int companyID);

    MemberToken getMembersToken(int companyID);
}
