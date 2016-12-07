package ro.hoptrop.security.mobile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.filter.GenericFilterBean;

import ro.hoptrop.core.exceptions.MobileAuthenticationException;
import ro.hoptrop.core.exceptions.SecurityException;
import ro.hoptrop.security.PrincipalUser;
import ro.hoptrop.service.AuthenticationService;

public class TokenAuthenticationFilter extends GenericFilterBean {

	private static final String EMPTY_PASSWORD = "";

	private static final Logger LOG = Logger.getLogger(TokenAuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	private AuthenticationEntryPoint entryPoint;

	private AuthenticationService authenticationService;

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		LOG.info("Token filter requested...");
		final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		try {
			final String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
			if (token == null) {
				LOG.info("No token provided...");
				throw new SecurityException("No token provided");
			}

			LOG.info("Token provided: " + token);

			PrincipalUser principal = authenticationService.authenticateByToken(token);

			final TokenAuthentication tokenAuthentication = new TokenAuthentication();
			tokenAuthentication.setAuthenticated(true);
			tokenAuthentication.setToken(token);
			tokenAuthentication.setPrincipal(principal);
			final Authentication auth = authenticationManager.authenticate(tokenAuthentication);

			SecurityContextHolder.getContext().setAuthentication(auth);
			chain.doFilter(request, response);
		} catch (Exception e) {
			LOG.info("Exception occurred while trying to login user based on token: " + e.getMessage());
			entryPoint.commence(httpServletRequest, httpServletResponse, new MobileAuthenticationException("Cannot authenticate"));
		}
	}

	public TokenAuthenticationFilter setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		return this;
	}

	public TokenAuthenticationFilter setEntryPoint(AuthenticationEntryPoint entryPoint) {
		this.entryPoint = entryPoint;
		return this;
	}

	public TokenAuthenticationFilter setAuthenticationService(AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
		return this;
	}

}
