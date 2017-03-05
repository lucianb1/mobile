package ro.hoptrop.test.client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.testng.Assert;
import ro.hoptrop.test.utils.AssertUtils;
import ro.hoptrop.web.request.company.CreateCompanyRequest;
import ro.hoptrop.web.response.company.CreateCompanyJsonResponse;

/**
 * Created by Luci on 04-Mar-17.
 */
@Component
public class CompanyControllerClient extends BaseControllerClient {

    public CreateCompanyJsonResponse createCompany(CreateCompanyRequest request, String token) {
        ResponseEntity<CreateCompanyJsonResponse> response = createCompanyRaw(request, token);
        AssertUtils.assertIsSuccessful(response);
        Assert.assertTrue(response.hasBody());
        return response.getBody();
    }

    public ResponseEntity<CreateCompanyJsonResponse> createCompanyRaw(CreateCompanyRequest request, String token) {
        String url = BASE_URL + "/secure/companies";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, token);
        HttpEntity entity = new HttpEntity<>(request, headers);
        return restTemplate.postForEntity(url, entity, CreateCompanyJsonResponse.class);
    }

}
