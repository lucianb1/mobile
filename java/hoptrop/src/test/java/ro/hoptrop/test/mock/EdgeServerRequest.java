package ro.hoptrop.test.mock;

import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

/**
 * Created by Luci on 05-Mar-17.
 */
public class EdgeServerRequest {

    private final String url;
    private final String token;
    private final RequestMethod method;
    private final Object body;
    private final Map<String, String> params;

    EdgeServerRequest(String url, String token, RequestMethod method, Object body, Map<String, String> params) {
        this.url = url;
        this.token = token;
        this.method = method;
        this.body = body;
        this.params = params;
    }

    public String getUrl() {
        return url;
    }

    public String getToken() {
        return token;
    }

    public RequestMethod getMethod() {
        return method;
    }

    public Object getBody() {
        return body;
    }

    public Map<String, String> getParams() {
        return params;
    }
}
