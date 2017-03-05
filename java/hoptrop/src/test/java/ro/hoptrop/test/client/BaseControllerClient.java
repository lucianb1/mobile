package ro.hoptrop.test.client;

import org.springframework.web.client.RestTemplate;

/**
 * Created by Luci on 05-Mar-17.
 */
public class BaseControllerClient {

    protected RestTemplate restTemplate = new RestTemplate();

    protected final String BASE_URL = "http://localhost:8080";

}
