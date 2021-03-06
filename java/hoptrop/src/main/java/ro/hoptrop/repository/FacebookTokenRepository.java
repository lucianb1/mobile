package ro.hoptrop.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.repository.rowmapper.FacebookTokenRowMapper;
import ro.hoptrop.model.token.facebook.FacebookToken;

@Repository
public class FacebookTokenRepository {

	private final FacebookTokenRowMapper rowMapper = new FacebookTokenRowMapper();
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public void createToken(int accountID, String token) {
		String sql = "INSERT INTO facebook_tokens (account_id, token) values (:accountID, :token)";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("accountID", accountID)
				.addValue("token", token);
		jdbcTemplate.update(sql, params);
	}
	
	public FacebookToken findToken(String token) {
		String sql = "SELECT * FROM facebook_tokens WHERE token = :token";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("token", token);
		List<FacebookToken> resultList = jdbcTemplate.query(sql, params, rowMapper);
		if (resultList.isEmpty()) {
			throw new NotFoundException();
		}
		return resultList.get(0);
	}
	
	public void deleteToken(int id) {
		String sql = "DELETE FROM facebook_tokens WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
		jdbcTemplate.update(sql, params);
	}
	
	public void deleteToken(String token) {
		String sql = "DELETE FROM facebook_tokens WHERE token = :token";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("token", token);
		jdbcTemplate.update(sql, params);
	}
	
}
