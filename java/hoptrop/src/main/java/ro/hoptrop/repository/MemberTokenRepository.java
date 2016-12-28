package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.repository.rowmapper.MemberTokenRowMapper;
import ro.hoptrop.model.token.member.MemberToken;
import ro.hoptrop.web.request.member.CreateMemberTokenRequest;

import java.util.List;

/**
 * Created by Luci on 18-Dec-16.
 */
@Repository
public class MemberTokenRepository {

    private static final MemberTokenRowMapper rowMapper = new MemberTokenRowMapper();

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public void createMemberTokens(List<CreateMemberTokenRequest> tokens) {
        String sql = "INSERT INTO member_tokens (company_id, token, is_admin) VALUES (:companyID, :token, :isAdmin)";
        MapSqlParameterSource[] params = tokens.stream()
                .map(item -> new MapSqlParameterSource()
                        .addValue("companyID", item.getCompanyID())
                        .addValue("token", item.getToken())
                        .addValue("isAdmin", item.isAdmin()))
                .toArray(MapSqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(sql, params);
    }

    public MemberToken findByToken(String token) {
        String sql = "SELECT * FROM member_tokens WHERE token = :token";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("token", token);
        try {
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    public List<MemberToken> getForCompany(int companyID) {
        String sql = "SELECT * FROM member_tokens WHERE company_id = :companyID";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("companyID", companyID);
        List<MemberToken> resultList = jdbcTemplate.query(sql, params, rowMapper);
        if (resultList.size() != 2) {
            throw new NotFoundException();
        }
        return resultList;
    }

    public MemberToken getMembersTokenForCompany(int companyID) {
        String sql = "SELECT * FROM member_tokens WHERE company_id = :companyID AND is_admin = 0";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("companyID", companyID);
        try {
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    public MemberToken getMemberAdminTokenForCompany(int companyID) {
        String sql = "SELECT * FROM member_tokens WHERE company_id = :companyID AND is_admin = 1";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("companyID", companyID);
        try {
            return jdbcTemplate.queryForObject(sql, params, rowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

}
