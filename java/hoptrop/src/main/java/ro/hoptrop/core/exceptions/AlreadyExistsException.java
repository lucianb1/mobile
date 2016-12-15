package ro.hoptrop.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Entity already exists")
public class AlreadyExistsException extends Exception {

	private static final long serialVersionUID = 7140551730679723000L;

	public AlreadyExistsException(String msg) {
		super(msg);
	}
	
}
