package si.rsj.pu.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Positive;
import si.rsj.pu.entity.enums.VolunteerRole;

public record UpdateVolunteerRequest(
        @Positive Integer ztsCode,
        String firstName,
        String lastName,
        VolunteerRole volunteerRole,
        String phoneNumber,
        @Email String email,
        Boolean active
) {
}
