package ro.hoptrop.core.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Null value is evalued as valid.
 * @author Luci
 *
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {

	String message() default "Invalid password";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
	
}
