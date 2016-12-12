package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.model.member.TimeTable;
import ro.hoptrop.repository.CompanyRepository;
import ro.hoptrop.service.CompanyService;

import java.util.List;

/**
 * Created by Luci on 12-Dec-16.
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    public void updateCompany(int id, String newName, Location newLocation, TimeTable newTimetable) {
    }

    @Override
    public List<Company> findCompaniesByName(String name) {
        return null;
    }
}
