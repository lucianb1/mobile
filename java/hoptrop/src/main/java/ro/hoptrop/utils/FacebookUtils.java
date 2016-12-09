package ro.hoptrop.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class FacebookUtils {

	private String appID;
	private String appSecret;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String createFacebookLongLivedToken(String token) {
		String url = "https://facebook.com/oauth/access_token";
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
				.queryParam("grant_type", "fb_exchange_token&amp")
				.queryParam("client_id", appID + "&amp")
				.queryParam("client_secret", appSecret + "&amp")
				.queryParam("fb_exchange_token", token);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<?> entity = new HttpEntity<>(headers);
		ResponseEntity<String> response = restTemplate.getForEntity(builder.build().encode().toUri(), String.class);
		System.out.println(response.getBody());
		throw new RuntimeException();
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
