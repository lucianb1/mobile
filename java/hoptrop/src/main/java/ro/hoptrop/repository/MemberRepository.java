package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.rowmapper.MemberFeatureRowMapper;
import ro.hoptrop.core.rowmapper.MemberRowMapper;
import ro.hoptrop.model.member.Member;
import ro.hoptrop.model.member.MemberFeature;
import ro.hoptrop.web.request.member.CreateMemberServiceRequest;
import ro.hoptrop.web.request.member.UpdateMemberServiceRequest;

import java.util.List;

/**
 * Created by Luci on 17-Dec-16.
 */
@Repository
public class MemberRepository {

    private static final MemberRowMapper memberRowMapper = new MemberRowMapper();
    private static final MemberFeatureRowMapper featureRowMapper = new MemberFeatureRowMapper();
    private static final String SELECT_MEMBER_QUERY = "SELECT m.id, m.company_id, m.account_id, m.name from members m ";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Member> findMembersByCompany(int companyID) {
        String sql = SELECT_MEMBER_QUERY + "WHERE company_id = :companyID order by order_nr";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("companyID", companyID);
        return jdbcTemplate.query(sql, params, memberRowMapper);
    }

    public List<Member> findActiveMembersByCompany(int companyID) {
        String sql = SELECT_MEMBER_QUERY + "WHERE company_id = :companyID AND is_active = 1 order by order_nr";
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
        String sql = SELECT_MEMBER_QUERY + "WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        try {
            return jdbcTemplate.queryForObject(sql, params, memberRowMapper);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException();
        }
    }

    public List<MemberFeature> findMemberServices(int id, int domainID) {
        String sql = "SELECT * FROM member_services WHERE member_id = id AND domain_id = :domainID ORDER BY order_nr";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("domainID", domainID);
        return jdbcTemplate.query(sql, params, featureRowMapper);
    }

    /**
     * Update only if they belong to the member
     */
    public void updateMemberServices(int memberID, List<UpdateMemberServiceRequest> request) {
        String sql = "UPDATE member_services SET name = :name, order_nr = :orderNr, duration = :duration, domain_id = domainID WHERE id = :id AND member_id = :memberID";
        MapSqlParameterSource[] params = request.stream()
                .map(item -> new MapSqlParameterSource()
                        .addValue("id", item.getId())
                        .addValue("memberID", memberID)
                        .addValue("duration", item.getDuration())
                        .addValue("orderNr", item.getOrderNr())
                        .addValue("domainID", item.getDomainID()))
                .toArray(MapSqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(sql, params);
    }

    public void createMemberServices(int memberID, List<CreateMemberServiceRequest> request) {
        String sql = "INSERT INTO member_services (name, member_id, duration, order_nr, domain_id) values (:name, :memberID, :duration, :orderNr, :domainID)";
        MapSqlParameterSource[] params = request.stream()
                .map(item -> new MapSqlParameterSource()
                        .addValue("name", item.getName())
                        .addValue("memberID", memberID)
                        .addValue("duration", item.getDuration())
                        .addValue("orderNr", item.getOrderNr())
                        .addValue("domainID", item.getDomainID()))
                .toArray(MapSqlParameterSource[]::new);
        jdbcTemplate.batchUpdate(sql, params);
    }

    /**
     * Delete only if they belong to the member
     */
    public void deleteMemberServices(int memberID, List<Integer> ids) {
        String sql = "DELETE FROM member_services WHERE member_id = :memberID AND id IN (:ids)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("ids", ids);
        jdbcTemplate.update(sql, params);
    }

    public void deleteMemberService(int memberID, int serviceID) {
        String sql = "DELETE FROM member_services WHERE id = :serviceID AND member_id = :memberID)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberID", memberID)
                .addValue("serviceID", serviceID);
        jdbcTemplate.update(sql, params);
    }

}
