package ro.hoptrop.core.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ro.hoptrop.core.constants.ValidationConstants;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	@Override
	public void initialize(ValidPassword validPassword) {
	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext arg1) {
		if (password == null) {
			return false;
		}
		if (password.contains(" ")) {
			return false;
		}
		if (password.length() < ValidationConstants.PASSWORD_MIN_LENGTH) {
			return false;
		}
		if (password.length() > ValidationConstants.PASSWORD_MAX_LENGTH) {
			return false;
		}
//		if (!password.matches("^[a-zA-Z0-9]*$")) {
//			return false;
//		}
//		// contains digits
//		if (!password.matches(".*\\d.*")) {
//			return false;
//		}
//		if (!hasUppercaseLetter(password)) {
//			return false;
//		}
//		if (!hasLowercaseLetter(password)) {
//			return false;
//		}
		return true;
	}

	/**
	 * Returns true if the given string contains at least one upper-case letter
	 * @param password
	 * @return
	 */
	private boolean hasUppercaseLetter(String password) {
		for (char c : password.toCharArray()) {
			if (Character.isUpperCase(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns true if the given string contains at least one lowercase letter
	 * @param password not null
	 * @return
	 */
	private boolean hasLowercaseLetter(String password) {
		for (char c : password.toCharArray()) {
			if (Character.isLowerCase(c)) {
				return true;
			}
		}
		return false;
	}
}
