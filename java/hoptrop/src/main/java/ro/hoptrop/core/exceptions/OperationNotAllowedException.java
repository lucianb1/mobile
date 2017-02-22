package ro.hoptrop.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Luci on 22-Feb-17.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Unauthorized")
public class OperationNotAllowedException extends RuntimeException {
    private static final long serialVersionUID = -795900359891521402L;

    public OperationNotAllowedException(String message) {
        super(message);
    }

    public OperationNotAllowedException() {
        super();
    }
}
