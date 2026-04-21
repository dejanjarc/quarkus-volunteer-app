package si.rsj.pu.model;

import si.rsj.pu.model.enums.VolunteerRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public record Volunteer(
        UUID id,
        int ztsCode,
        String firstName,
        String lastName,
        VolunteerRole volunteerRole,
        String phoneNumber,
        String email,
        boolean active,
        OffsetDateTime joinedAt
) {
}