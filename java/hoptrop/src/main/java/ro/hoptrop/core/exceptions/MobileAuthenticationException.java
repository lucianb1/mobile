package ro.hoptrop.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Mobile authentication failed")
public class MobileAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = 32322L;

	public MobileAuthenticationException(String msg) {
		super(msg);
	}

}
