package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.rowmapper.MemberTokenRowMapper;
import ro.hoptrop.model.token.member.MemberToken;

/**
 * Created by Luci on 18-Dec-16.
 */
@Repository
public class MemberTokenRepository {

    private static final MemberTokenRowMapper rowMapper = new MemberTokenRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void createMemberToken(int companyID, String token, boolean isAdmin) {
        String sql = "INSERT INTO member_tokens (company_id, token, is_admin) values (:companyID, :token, :isAdmin";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("companyID", companyID)
                .addValue("token", token)
                .addValue("isAdmin", isAdmin);
        jdbcTemplate.update(sql, params);
    }

    public MemberToken findByToken(String token) {
        String sql = "SELECT * FROM member_tokens where token = :token";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("token", token);
        try {
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

}
