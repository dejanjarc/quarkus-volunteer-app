package si.rsj.pu.service.command;

import si.rsj.pu.entity.enums.VolunteerRole;

public record UpdateVolunteerCommand(
        Integer ztsCode,
        String firstName,
        String lastName,
        VolunteerRole volunteerRole,
        String phoneNumber,
        String email,
        Boolean active
) {
}
