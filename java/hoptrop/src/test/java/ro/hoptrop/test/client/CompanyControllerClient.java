package ro.hoptrop.test.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.testng.Assert;
import ro.hoptrop.test.mock.EdgeServer;
import ro.hoptrop.test.mock.EdgeServerRequest;
import ro.hoptrop.test.mock.EdgeServerRequestBuilder;
import ro.hoptrop.test.mock.EdgeServerResponse;
import ro.hoptrop.test.utils.AssertUtils;
import ro.hoptrop.web.request.company.CreateCompanyRequest;
import ro.hoptrop.web.response.company.CreateCompanyJsonResponse;

/**
 * Created by Luci on 04-Mar-17.
 */
@Component
public class CompanyControllerClient extends BaseControllerClient {

    @Autowired
    private EdgeServer edgeServer;

    public CreateCompanyJsonResponse createCompany(CreateCompanyRequest request, String token) {
        EdgeServerResponse<CreateCompanyJsonResponse> response = createCompanyRaw(request, token);
        AssertUtils.assertIsSuccessful(response);
        Assert.assertNotNull(response.getContent());
        return response.getContent();
    }

    public EdgeServerResponse<CreateCompanyJsonResponse> createCompanyRaw(CreateCompanyRequest request, String token) {
        EdgeServerRequest serverRequest = new EdgeServerRequestBuilder()
            .setUrl("/secure/companies")
            .setBody(request)
            .setMethod(RequestMethod.POST)
            .setToken(token)
            .build();
        return edgeServer.executeRequest(serverRequest, CreateCompanyJsonResponse.class);
    }

}
