package ro.hoptrop.test.mock;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ro.hoptrop.utils.JsonUtils;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.Map;

/**
 * Created by Luci on 05-Mar-17.
 */
@Service
public class EdgeServer {

    private MockMvc mockMvc;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    private WebApplicationContext context;

    @PostConstruct
    public void initMvc() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
            .addFilter(springSecurityFilterChain)
            .build();
    }

    public <T> EdgeServerResponse<T> executeRequest(EdgeServerRequest request, Class<T> responseType) {
        MockHttpServletRequestBuilder mockRequest;
        switch (request.getMethod()) {
            case GET:
                mockRequest = MockMvcRequestBuilders.get(request.getUrl());
                break;
            case POST:
                mockRequest = MockMvcRequestBuilders.post(request.getUrl());
                break;
            case PUT:
                mockRequest = MockMvcRequestBuilders.put(request.getUrl());
                break;
            case DELETE:
                mockRequest = MockMvcRequestBuilders.delete(request.getUrl());
                break;
            default:
                throw new RuntimeException("Invalid method value");
        }

        mockRequest.accept(MediaType.APPLICATION_JSON);
        mockRequest.contentType(MediaType.APPLICATION_JSON);

        if (request.getBody() != null) {
            mockRequest.content(JsonUtils.toJson(request.getBody()));
        }
        if (request.getToken() != null) {
            mockRequest.header(HttpHeaders.AUTHORIZATION, request.getToken());
        }
        Map<String, String> params = request.getParams();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            mockRequest.param(entry.getKey(), entry.getValue());
        }
        try {
            MvcResult mvcResult = this.mockMvc.perform(mockRequest).andReturn();
            MockHttpServletResponse response = mvcResult.getResponse();
            T result = null;
            if (response.getStatus() == HttpStatus.OK.value() && StringUtils.isNotBlank(response.getContentAsString()) && responseType != null) {
                result = JsonUtils.fromJson(response.getContentAsString(), responseType);
            }
            return new EdgeServerResponse<>(response.getStatus(), result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public MvcResult execute(String token, Object requestBody) {
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/secure/companies");
        request.accept(MediaType.APPLICATION_JSON);
        request.contentType(MediaType.APPLICATION_JSON);
        request.header(HttpHeaders.AUTHORIZATION, token);
        request.content(JsonUtils.toJson(requestBody));
        try {
            return this.mockMvc.perform(request).andReturn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
