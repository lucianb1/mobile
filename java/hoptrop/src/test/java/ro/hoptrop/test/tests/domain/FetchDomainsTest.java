package ro.hoptrop.test.tests.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import ro.hoptrop.test.client.DomainControllerClient;
import ro.hoptrop.test.config.BaseTestClass;
import ro.hoptrop.test.mock.EdgeServerResponse;
import ro.hoptrop.test.utils.AssertUtils;
import ro.hoptrop.web.response.domain.DomainsJsonResponse;

/**
 * Created by Luci on 06-Mar-17.
 */

public class FetchDomainsTest extends BaseTestClass{

    @Autowired
    private DomainControllerClient domainClient;

    @Test
    public void fetchDomainsTest(){
        String token = getSuperAdminToken();
        EdgeServerResponse<DomainsJsonResponse> response = domainClient.getDomains(token);
        AssertUtils.assertIsSuccessful(response);
        Assert.assertNotNull(response.getContent());
        Assert.assertNotNull(response.getContent().getDomains());
        Assert.assertFalse(response.getContent().getDomains().isEmpty());
    }

}
