package ro.hoptrop.core.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ro.hoptrop.model.account.Account;
import ro.hoptrop.model.account.AccountType;

public class AccountRowMapper implements RowMapper<Account>{

	@Override
	public Account mapRow(ResultSet rs, int arg1) throws SQLException {
		return Account.builder()
				.setEmail(rs.getString("email"))
				.setPassword(rs.getString("password"))
				.setId(rs.getInt("id"))
				.setPhone(rs.getString("phone"))
				.setType(AccountType.valueOf(rs.getString("role")))
				.setName(rs.getString("name"))
				.build();
	}

}
