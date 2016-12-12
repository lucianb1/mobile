package ro.hoptrop.core.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.company.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 12-Dec-16.
 */
public class CompanyRowMapper implements RowMapper<Company> {
    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
        return null;
    }
}
