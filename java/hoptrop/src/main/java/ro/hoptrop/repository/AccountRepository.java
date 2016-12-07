package ro.hoptrop.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.rowmapper.AccountRowMapper;
import ro.hoptrop.model.account.Account;

@Repository
public class AccountRepository {

	private final AccountRowMapper rowMapper = new AccountRowMapper();
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public void createAccount() {
		
	}
	
	public Account findAccount(int id) {
		String sql = "SELECT * from accounts where id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
		List<Account> accounts = jdbcTemplate.query(sql, params, rowMapper);
		if (accounts.isEmpty()) {
			throw new NotFoundException();
		}
		return accounts.get(0);
	}
	
	public Account findAccount(String email) {
		String sql = "SELECT * from accounts where email = :email";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("email", email);
		List<Account> accounts = jdbcTemplate.query(sql, params, rowMapper);
		if (accounts.isEmpty()) {
			throw new NotFoundException();
		}
		return accounts.get(0);
	}
	
	public void updateAccount() {
		
	}
	
}
