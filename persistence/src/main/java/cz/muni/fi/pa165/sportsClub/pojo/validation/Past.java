package cz.muni.fi.pa165.sportsClub.pojo.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PastValidator.class)
@Documented
public @interface Past {

	String message() default "it.jdev.example.jsr310.validator.Past.message";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
