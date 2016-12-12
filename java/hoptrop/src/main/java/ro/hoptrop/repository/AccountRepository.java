package ro.hoptrop.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.hoptrop.core.exceptions.NotFoundException;
import ro.hoptrop.core.rowmapper.AccountRowMapper;
import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;

import java.util.List;

@Repository
public class AccountRepository {

	private static final AccountRowMapper rowMapper = new AccountRowMapper();
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	public Account createAccount(String email, String password, String name, String phone, AccountType type) {
		String sql = "INSERT INTO accounts (email, password, name, phone, role) values (:email, :password, :name, :phone, :accountType)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("email", email)
				.addValue("password", password)
				.addValue("phone", phone)
				.addValue("name", name)
				.addValue("accountType", type.name());
		jdbcTemplate.update(sql, params, keyHolder);
		return this.findAccount(keyHolder.getKey().intValue());
	}
	
	public Account findAccount(int id) {
		String sql = "SELECT * FROM accounts WHERE id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
		List<Account> accounts = jdbcTemplate.query(sql, params, rowMapper);
		if (accounts.isEmpty()) {
			throw new NotFoundException();
		}
		return accounts.get(0);
	}
	
	public Account findAccount(String email) {
		String sql = "SELECT * FROM accounts WHERE email = :email";
		MapSqlParameterSource params = new MapSqlParameterSource().addValue("email", email);
		List<Account> accounts = jdbcTemplate.query(sql, params, rowMapper);
		if (accounts.isEmpty()) {
			throw new NotFoundException();
		}
		return accounts.get(0);
	}

	public void updateAccountDetails(int id, String newName, String newPhone) {
	    if (newName == null || newPhone == null) {
	        throw new IllegalArgumentException("Null argument");
	    }
	    String sql = "UPDATE accounts SET name = :newName, phone = :newPhone where id = :id";
	    MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id)
                .addValue("newName", newName)
                .addValue("newPhone", newPhone);
	    jdbcTemplate.update(sql, params);
    }
	
	public void updateAccountPassword(int accountID, String password) {
		String sql = "UPDATE accounts SET password = :password where id = :id";
		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("password", password)
				.addValue("id", accountID);
		jdbcTemplate.update(sql, params);
	}
	
}
