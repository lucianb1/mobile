package ro.hoptrop.core.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.company.Company;
import ro.hoptrop.model.company.Location;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 12-Dec-16.
 */
public class CompanyRowMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet rs, int i) throws SQLException {
        return Company.builder()
                .setId(rs.getInt("id"))
                .setName(rs.getString("name"))
                .setMembersToken(rs.getString("members_token"))
                .setLocation(Location.builder()
                        .setAddress(rs.getString("address"))
                        .setLatitude(rs.getDouble("lat"))
                        .setLongitude(rs.getDouble("long"))
                        .build())
                .setOrderNr(rs.getInt("order_nr"))
                .build();
    }
}
