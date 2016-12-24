package ro.hoptrop.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Luci on 24-Dec-16.
 */
@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Entity already used")
public class AlreadyUsedException extends Exception {

    private static final long serialVersionUID = 714055173619723000L;

    public AlreadyUsedException(String msg) {
        super(msg);
    }

}
