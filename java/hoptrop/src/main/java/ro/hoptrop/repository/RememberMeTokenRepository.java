package ro.hoptrop.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.rowmapper.RememberMeTokenRowMapper;
import ro.hoptrop.model.token.RememberMeToken;

@Repository
public class RememberMeTokenRepository {

	private final RememberMeTokenRowMapper rowMapper = new RememberMeTokenRowMapper();
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	
	public void createToken(int accountID, String token) {
		String sql = "INSERT INTO remember_me_tokens (account_id, token) values (:accountID, :token)";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("accountID", accountID)
				.addValue("token", token);
		jdbcTemplate.update(sql, params);
	}
	
	public RememberMeToken findToken(String token) {
		String sql = "SELECT * FROM remember_me_tokens WHERE token = :token";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("token", token);
		List<RememberMeToken> resultList = jdbcTemplate.query(sql, params, rowMapper);
		if (resultList.isEmpty()) {
			throw new NotFoundException();
		}
		return resultList.get(0);
	}
	
	public void deleteToken(int id) {
		String sql = "DELETE FROM remember_me_tokens WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
		jdbcTemplate.update(sql, params);
	}
	
}
