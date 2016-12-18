package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.rowmapper.MemberRowMapper;
import ro.hoptrop.model.member.Member;

import java.util.List;

/**
 * Created by Luci on 17-Dec-16.
 */
@Repository
public class MemberRepository {

    private static final MemberRowMapper memberRowMapper = new MemberRowMapper();
    private static final String SELECT_MEMBER_QUERY = "SELECT m.id, m.name from members m ";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Member> findMembersByCompany(int companyID) {
        String sql = SELECT_MEMBER_QUERY + "where company_id = :companyID order by order_nr";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("companyID", companyID);
        return jdbcTemplate.query(sql, params, memberRowMapper);
    }

    public Member createMember(int accountID, int companyID, String name) {
        String sql = "INSERT INTO members (account_id, company_id, name) VALUES (:accountID, :companyID, :name)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("accountID", accountID)
                .addValue("companyID", companyID)
                .addValue("name", name);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(sql, params, keyHolder);
        return this.findMember(keyHolder.getKey().intValue());
    }

    public Member findMember(int id) {
        String sql = SELECT_MEMBER_QUERY + "where id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, memberRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }


}
