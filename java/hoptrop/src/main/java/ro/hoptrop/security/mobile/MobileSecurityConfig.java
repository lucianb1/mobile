package ro.hoptrop.security.mobile;

import javax.servlet.Filter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ro.hoptrop.service.AuthenticationService;

@Configuration
public class MobileSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOG = Logger.getLogger(MobileSecurityConfig.class);

	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
	@Autowired
	private TokenAuthenticationProvider provider;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthenticationService authenticationService;
	
//	@Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(provider);
//    }
	
	@Autowired
	  public void configureGlobal(AuthenticationManagerBuilder auth) {
	    auth.authenticationProvider(provider);
	  }
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	     web.ignoring()
	        .antMatchers("/mobile/login/**", "/mobile/forgotpassword", "/mobile/register");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		 http
		 	.antMatcher("/mobile/**")
		 	.authorizeRequests()
		 		.anyRequest().authenticated()
		 		.and()
		 	.addFilterBefore(getAuthTokenFilter(), UsernamePasswordAuthenticationFilter.class)
		 	.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
	        .exceptionHandling()
	             .authenticationEntryPoint(restAuthenticationEntryPoint)
	             .and()
         .csrf().disable();

		if ("true".equals(System.getProperty("httpsOnly"))) {
			LOG.info("launching the application in HTTPS-only mode");
			http.requiresChannel().anyRequest().requiresSecure();
		}
	}
	
	public Filter getAuthTokenFilter() {
		return new TokenAuthenticationFilter()
			.setAuthenticationManager(authenticationManager)
			.setEntryPoint(restAuthenticationEntryPoint)
			.setAuthenticationService(authenticationService);
	}
	
}
