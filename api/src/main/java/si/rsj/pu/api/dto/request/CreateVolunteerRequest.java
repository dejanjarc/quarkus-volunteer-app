package si.rsj.pu.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import si.rsj.pu.model.enums.VolunteerRole;

public record CreateVolunteerRequest(
    @Positive int ztsCode,
    @NotBlank String firstName,
    @NotBlank String lastName,
    @NotNull VolunteerRole volunteerRole,
    String phoneNumber,
    @Email String email
) {

}

