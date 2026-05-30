package si.rsj.pu.api.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EventDatesValidator implements ConstraintValidator<ValidEventDates, HasEventDates> {

    @Override
    public boolean isValid(HasEventDates value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        if (value.startDate() == null || value.endDate() == null) {
            return true;
        }

        if (!value.endDate().isBefore(value.startDate())) {
            return true;
        }

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate("endDate must not be before startDate")
                .addPropertyNode("endDate")
                .addConstraintViolation();

        return false;
    }
}