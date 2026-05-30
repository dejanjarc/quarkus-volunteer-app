package si.rsj.pu.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = EventDatesValidator.class)
@Target(TYPE)
@Retention(RUNTIME)
public @interface ValidEventDates {

    String message() default "endDate must not be before startDate";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}