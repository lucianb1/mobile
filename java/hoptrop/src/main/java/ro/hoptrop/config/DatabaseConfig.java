package ro.hoptrop.config;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ConfigurationProperties(prefix = "params.datasource")
public class DatabaseConfig extends HikariConfig {

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		return new HikariDataSource(this);
	}

	@Bean
	public NamedParameterJdbcTemplate createNamedJDBCTemplate(DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

}
