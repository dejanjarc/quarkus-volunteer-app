package si.rsj.pu.service.command;

import si.rsj.pu.model.enums.VolunteerRole;

public record CreateVolunteerCommand(
        int ztsCode,
        String firstName,
        String lastName,
        VolunteerRole volunteerRole,
        String phoneNumber,
        String email
) {
}