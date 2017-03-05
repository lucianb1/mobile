package ro.hoptrop.test.tests.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;
import ro.hoptrop.model.company.Location;
import ro.hoptrop.test.client.CompanyControllerClient;
import ro.hoptrop.test.config.BaseTestClass;
import ro.hoptrop.test.utils.AssertUtils;
import ro.hoptrop.web.request.company.CreateCompanyRequest;
import ro.hoptrop.web.response.company.CreateCompanyJsonResponse;

import java.util.Collections;

/**
 * Created by Luci on 04-Mar-17.
 */
public class CreateCompanyTest extends BaseTestClass {

    @Autowired
    private CompanyControllerClient companyClient;

    @Test
    public void createCompanyTest() {
        String token = getSuperAdminToken();
        Location location = randomLocation();
        String name = randomString(10);
        CreateCompanyRequest request = new CreateCompanyRequest()
            .setLocation(location)
            .setName(name)
            .setDomains(Collections.singleton(1));

        ResponseEntity<CreateCompanyJsonResponse> response = companyClient.createCompanyRaw(request, token);
        AssertUtils.assertIsSuccessful(response);
        CreateCompanyJsonResponse responseBody = response.getBody();
        Assert.assertEquals(responseBody.getLocation(), location);
        Assert.assertEquals(responseBody.getName(), name);


    }


}
