package ro.hoptrop.repository.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import ro.hoptrop.model.token.facebook.FacebookToken;

public class FacebookTokenRowMapper implements RowMapper<FacebookToken>{

	@Override
	public FacebookToken mapRow(ResultSet rs, int arg1) throws SQLException {
		return FacebookToken.builder()
				.setId(rs.getInt("id"))
				.setAccountID(rs.getInt("account_id"))
				.setCreationDate(rs.getDate("created_on"))
				.setToken(rs.getString("token"))
				.build();
	}

}
