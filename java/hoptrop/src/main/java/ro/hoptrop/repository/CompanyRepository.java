package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.repository.rowmapper.CompanyRowMapper;
import ro.hoptrop.core.sql.SqlQueryBuilder;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.model.timetable.WeekTimetable;
import ro.hoptrop.utils.JsonUtils;
import ro.hoptrop.utils.SqlUtils;

import java.util.List;
import java.util.Set;

/**
 * Created by Luci on 12-Dec-16.
 */
@Repository
public class CompanyRepository {

    private static final CompanyRowMapper rowMapper = new CompanyRowMapper();
    private static final String SELECT_CLAUSE = "SELECT c.id, c.name, X(c.coordinates) AS lon, Y(c.coordinates) AS lat, c.address, c.order_nr, member_token, member_admin_token FROM companies c ";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Company createCompany(String name, Location location, int orderNr, String memberToken, String memberAdminToken) {
        String sql = "INSERT INTO companies (name, address, coordinates, order_nr, member_token, member_admin_token) " +
            "values (:name, :address, GeomFromText(:coordinates), :orderNr, :memberToken, :memberAdminToken)";
        String geometryParam = location.hasCoordinates() ? SqlUtils.createPointFromLocation(location.getLongitude(), location.getLatitude()) : null;
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", name)
            .addValue("address", location.getAddress())
            .addValue("coordinates", geometryParam)
            .addValue("orderNr", orderNr)
            .addValue("memberToken", memberToken)
            .addValue("memberAdminToken", memberAdminToken);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return this.findCompany(keyHolder.getKey().intValue());
    }

    public void setCompanyDomains(int companyID, Set<Integer> domains) {
        String sql = "INSERT INTO companies_to_domains (company_id, domain_id) VALUES (:companyID, :domainID)";
        MapSqlParameterSource[] params = domains.stream()
            .map(item -> new MapSqlParameterSource()
                .addValue("companyID", companyID)
                .addValue("domainID", item))
            .toArray(MapSqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(sql, params);
    }

    public void updateCompany(int id, String newName, Location newLocation, WeekTimetable newTimeTable, int newOrderNr) {
        String sql = "UPDATE companies SET name = :newName, address = :newAddress, coordinates = GeomFromText(:newCoordinates), timetable = :newTimetable, order_nr = :orderNr WHERE id = :id";
        String geometryParam = newLocation.hasCoordinates() ? SqlUtils.createPointFromLocation(newLocation.getLongitude(), newLocation.getLatitude()) : null;
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("newName", newName)
            .addValue("newAddress", newLocation.getAddress())
            .addValue("newCoordinates", geometryParam)
            .addValue("newTimetable", JsonUtils.toJson(newTimeTable))
            .addValue("orderNr", newOrderNr);
        jdbcTemplate.update(sql, params);
    }

    public void updateCompanyMembersToken(int id, String newToken) {
        String sql = "UPDATE companies SET members_token = :token WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("id", id)
            .addValue("token", newToken);
        jdbcTemplate.update(sql, params);
    }

    public Company findCompany(int id) {
        String sql = SELECT_CLAUSE + "WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        List<Company> resultList = jdbcTemplate.query(sql, params, rowMapper);
        if (resultList.isEmpty()) {
            throw new NotFoundException();
        }
        return resultList.get(0);
    }

    public List<Company> findCompaniesByNameAndDomain(int domainID, String name) {
        SqlQueryBuilder builder = new SqlQueryBuilder(SELECT_CLAUSE)
            .append("INNER JOIN companies_to_domains cd ON c.id = cd.company_id ")
            .append("INNER JOIN company_domains d ON cd.domain_id = d.id ")
            .append("WHERE d.id = :domainID ")
            .conditionalAppend(name != null, "AND LOWER(c.name) like %:name%");

        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("name", name != null ? name.toLowerCase() : null)
            .addValue("domainID", domainID);
        return jdbcTemplate.query(builder.toString(), params, rowMapper);
    }

    public boolean membersTokenExist(String token) {
        String sql = "SELECT count(id) FROM companies WHERE members_token = :token";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("token", token);
        return jdbcTemplate.queryForObject(sql, params, Integer.class) > 0;
    }

    public String getMemberAdminToken(int companyID) {
        String sql = "SELECT member_admin_token FROM companies WHERE id = :id";
        try {
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource().addValue("id", companyID), String.class);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }


}
