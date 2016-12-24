package ro.hoptrop.core.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.member.Member;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 18-Dec-16.
 */
public class MemberRowMapper implements RowMapper<Member> {

    @Override
    public Member mapRow(ResultSet rs, int i) throws SQLException {
        return Member.builder()
                .setId(rs.getInt("id"))
                .setAccountID(rs.getInt("accountID"))
                .setCompanyID(rs.getInt("company_id"))
                .setName(rs.getString("name"))
                .setIsActive(rs.getBoolean("is_active"))
                .build();
    }
}
