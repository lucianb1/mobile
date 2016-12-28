package ro.hoptrop.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.token.member.MemberToken;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 18-Dec-16.
 */
public class MemberTokenRowMapper implements RowMapper<MemberToken> {

    @Override
    public MemberToken mapRow(ResultSet rs, int i) throws SQLException {
        return MemberToken.builder()
                .setId(rs.getInt("id"))
                .setIsAdmin(rs.getBoolean("is_admin"))
                .setCompanyID(rs.getInt("company_id"))
                .setToken(rs.getString("token"))
                .build();
    }
}
