package ro.hoptrop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@ConfigurationProperties(prefix = "facebook")
public class FacebookUtils {

	private String appID;
	private String appSecret;
	private static final String SEPARATOR = "&";
	private static final String EQUAL = "=";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String createFacebookLongLivedToken(String token) {
		String url = "https://graph.facebook.com/oauth/access_token";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("grant_type", "fb_exchange_token")
				.queryParam("client_id", appID)
				.queryParam("client_secret", appSecret)
				.queryParam("fb_exchange_token", token);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<>(headers);
		URI finalUrl = builder.build().encode().toUri();
		ResponseEntity<String> response = restTemplate.getForEntity(finalUrl, String.class);
		return extractToken(response.getBody());
	}

	private String extractToken(String response) {
		String[] params = response.split(SEPARATOR);
		String tokenParam = params[0];
		return tokenParam.split(EQUAL)[1];
	}
	
	public void setAppID(String appID) {
		this.appID = appID;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	
	
}
