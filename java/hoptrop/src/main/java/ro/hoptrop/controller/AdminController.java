package ro.hoptrop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.hoptrop.service.CompanyService;

/**
 * Created by Luci on 12-Dec-16.
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CompanyService companyService;

    @RequestMapping("/company/{companyID}/token/superadmin")
    public String getSuperAdminToken(int companyID) {
        return companyService.getMemberAdminToken(companyID);
    }

}
