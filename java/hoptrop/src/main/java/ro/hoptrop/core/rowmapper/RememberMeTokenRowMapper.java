package ro.hoptrop.core.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ro.hoptrop.model.token.RememberMeToken;

public class RememberMeTokenRowMapper implements RowMapper<RememberMeToken> {

	@Override
	public RememberMeToken mapRow(ResultSet rs, int arg1) throws SQLException {
		return RememberMeToken.builder()
				.setId(rs.getInt("id"))
				.setAccountID(rs.getInt("account_id"))
				.setCreationDate(rs.getDate("created_on"))
				.setToken(rs.getString("token"))
				.build();
	}

}
