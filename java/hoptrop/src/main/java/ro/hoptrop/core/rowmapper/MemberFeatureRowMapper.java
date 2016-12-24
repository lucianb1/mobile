package ro.hoptrop.core.rowmapper;

import org.springframework.jdbc.core.RowMapper;
import ro.hoptrop.model.member.MemberFeature;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Luci on 24-Dec-16.
 */
public class MemberFeatureRowMapper implements RowMapper<MemberFeature> {

    @Override
    public MemberFeature mapRow(ResultSet rs, int i) throws SQLException {
        return MemberFeature.builder()
                .setId(rs.getInt("id"))
                .setDomainID(rs.getInt("domain_id"))
                .setName(rs.getString("rs"))
                .setMemberID(rs.getInt("member_id"))
                .setDuration(rs.getInt("duration"))
                .build();
    }
}
