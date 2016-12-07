package ro.hoptrop.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class SecurityException extends RuntimeException {

	private static final long serialVersionUID = -795900359201521402L;
	
	public SecurityException(String message) {
		super(message);
	}

}
