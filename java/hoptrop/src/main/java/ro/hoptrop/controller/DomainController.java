package ro.hoptrop.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Luci on 12-Dec-16.
 */
@RestController
@RequestMapping("/domains")
public class DomainController {

    @RequestMapping(method = RequestMethod.GET)
    public void getAll() {

    }

}
