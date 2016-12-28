package ro.hoptrop.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.domain.CompanyDomain;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * Created by Luci on 12-Dec-16.
 */
public class CompanyDomainRowMapper implements RowMapper<CompanyDomain> {

    @Override
    public CompanyDomain mapRow(ResultSet resultSet, int i) throws SQLException {
        return CompanyDomain.builder()
                .setId(resultSet.getInt("id"))
                .setName(resultSet.getString("name"))
                .setOrderNr(resultSet.getInt("order_nr"))
                .build();
    }
}
