package si.rsj.pu.api.dto.request;

import jakarta.validation.constraints.Email;
import si.rsj.pu.entity.enums.VolunteerRole;

public record UpdateVolunteerRequest(
        Integer ztsCode,
        String firstName,
        String lastName,
        VolunteerRole volunteerRole,
        String phoneNumber,
        @Email String email,
        Boolean active
) {
}
