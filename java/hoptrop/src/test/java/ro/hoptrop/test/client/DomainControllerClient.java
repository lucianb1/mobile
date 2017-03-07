package ro.hoptrop.test.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import ro.hoptrop.test.mock.EdgeServer;
import ro.hoptrop.test.mock.EdgeServerRequest;
import ro.hoptrop.test.mock.EdgeServerRequestBuilder;
import ro.hoptrop.test.mock.EdgeServerResponse;
import ro.hoptrop.web.response.domain.DomainsJsonResponse;

/**
 * Created by Luci on 06-Mar-17.
 */
@Component
public class DomainControllerClient {

    @Autowired
    private EdgeServer edgeServer;

    public EdgeServerResponse<DomainsJsonResponse> getDomains(String token) {
        EdgeServerRequest request = new EdgeServerRequestBuilder()
            .setMethod(RequestMethod.GET)
            .setUrl("/domain/companies")
            .setToken(token)
            .build();
        return edgeServer.executeRequest(request, DomainsJsonResponse.class);
    }

}
