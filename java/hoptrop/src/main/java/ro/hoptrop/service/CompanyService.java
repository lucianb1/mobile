package ro.hoptrop.service;

import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;

import java.util.List;

/**
 * Created by Luci on 12-Dec-16.
 */
public interface CompanyService {

    void setCompanyLocation(double longitude, double latitude);

    void updateCompany(int id, String newName, Location newLocation);

    List<Company> findCompaniesByNameAndDomain(int domainID, String name);

    Company findByID(int id);

    String regenerateMembersToken(int companyID);
}
