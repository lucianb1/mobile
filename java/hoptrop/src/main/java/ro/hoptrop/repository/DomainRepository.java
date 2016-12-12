package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.rowmapper.CompanyDomainRowMapper;
import ro.hoptrop.model.domain.CompanyDomain;

import java.util.List;

/**
 * Created by Luci on 12-Dec-16.
 */
@Repository
public class DomainRepository {

    private static final CompanyDomainRowMapper rowMapper = new CompanyDomainRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<CompanyDomain> getAllCompaniesDomains() {
        String sql = "SELECT * FROM company_domains order by order_nr";
        return jdbcTemplate.query(sql, rowMapper);
    }

}
