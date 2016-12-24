package ro.hoptrop.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Luci on 24-Dec-16.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad request")
public class BadRequestException extends RuntimeException {

    private static final long serialVersionUID = 7140551230679723000L;

    public BadRequestException(String msg) {
        super(msg);
    }

}