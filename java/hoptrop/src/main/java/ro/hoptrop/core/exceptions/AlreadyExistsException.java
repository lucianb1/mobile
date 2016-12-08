package ro.hoptrop.core.exceptions;

public class AlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 7140551730679723000L;

	public AlreadyExistsException(String msg) {
		super(msg);
	}
	
}
