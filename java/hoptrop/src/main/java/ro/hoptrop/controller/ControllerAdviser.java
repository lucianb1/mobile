package ro.hoptrop.controller;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by Luci on 18-Dec-16.
 */
@ControllerAdvice
public class ControllerAdviser {

    private static final Logger LOG = Logger.getLogger(ControllerAdviser.class);

    @ExceptionHandler
    public void handle(Exception e) throws Exception {
        LOG.info("Exception occurred", e);
        throw e;
    }

}
