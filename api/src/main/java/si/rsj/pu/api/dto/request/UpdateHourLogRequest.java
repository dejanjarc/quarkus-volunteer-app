package si.rsj.pu.api.dto.request;

import jakarta.validation.constraints.Positive;
import si.rsj.pu.entity.enums.EventRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public record UpdateHourLogRequest(
    UUID volunteerId,
    UUID eventId,
    EventRole eventRole,
    OffsetDateTime workDate,
    @Positive Integer hoursWorked,
    String description
) {
}
