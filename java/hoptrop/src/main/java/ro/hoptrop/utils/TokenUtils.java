package ro.hoptrop.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenUtils {

	public static String generateToken() {
		return RandomStringUtils.randomAlphanumeric(50);
	}
	
}
