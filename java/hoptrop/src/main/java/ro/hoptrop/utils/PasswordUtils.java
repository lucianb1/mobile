package ro.hoptrop.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtils {

	private static final int PASSWORD_LENGTH = 8;
	
	public static String generateRandomPassword() {
		return RandomStringUtils.randomAlphanumeric(PASSWORD_LENGTH);
	}
	
}
