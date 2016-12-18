package ro.hoptrop.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.hoptrop.model.domain.CompanyDomain;
import ro.hoptrop.repository.DomainRepository;
import ro.hoptrop.service.DomainService;

import java.util.List;

/**
 * Created by Luci on 12-Dec-16.
 */
@Service
public class DomainServiceImpl implements DomainService{

    @Autowired
    private DomainRepository domainRepository;

    @Override
    public List<CompanyDomain> getAllCompanyDomains() {
        return domainRepository.getAllCompaniesDomains();
    }

}
