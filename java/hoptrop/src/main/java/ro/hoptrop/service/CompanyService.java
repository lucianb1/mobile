package ro.hoptrop.service;

import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.model.timetable.Timetable;

import java.util.List;

/**
 * Created by Luci on 12-Dec-16.
 */
public interface CompanyService {

    void updateCompany(int id, String newName, Location newLocation, Timetable newTimetable);

    List<Company> findCompaniesByNameAndDomain(int domainID, String name);

    String regenerateMembersToken(int companyID);
}
