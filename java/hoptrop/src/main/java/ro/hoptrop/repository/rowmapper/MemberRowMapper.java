package ro.hoptrop.repository.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.member.MemberStatus;

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
            .setAccountID(rs.getInt("account_id"))
            .setCompanyID(rs.getInt("company_id"))
            .setStatus(MemberStatus.valueFrom(rs.getString("status")))
            .setName(rs.getString("name"))
            .build();
    }
}
