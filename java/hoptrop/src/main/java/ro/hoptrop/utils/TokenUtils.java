package ro.hoptrop.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class TokenUtils {

	private static final int TOKEN_LENGTH = 50;
	private static final int MEMBERS_TOKEN_LENGTH = 8;

	public static String generateToken() {
		return RandomStringUtils.randomAlphanumeric(TOKEN_LENGTH);
	}

	public static String generateMembersToken() {
		return RandomStringUtils.randomAlphanumeric(MEMBERS_TOKEN_LENGTH);
	}
	
}
