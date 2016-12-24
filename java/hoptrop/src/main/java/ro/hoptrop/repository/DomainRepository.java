package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

    public void createCompanyDomain(String name, int orderNr) {
        String sql = "INSERT INTO company_domains (name, order_nr) VALUES (:name, :orderNr)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", name)
                .addValue("orderNr", orderNr);
        jdbcTemplate.update(sql, params);
    }

//    public List<Integer> getDomainsForCompany(int companyID) {
//        String sql = "SELECT d.id FROM company_domains d INNER JOIN company_to_domains j ON j.domain_id = d.id WHERE j.company_id = :companyID";
//        return jdbcTemplate.queryForList(sql, new MapSqlParameterSource().addValue("companyID", companyID), Integer.class);
//    }

}
