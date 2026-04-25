package si.rsj.pu.service.command;

import si.rsj.pu.entity.enums.EventRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateHourLogCommand(
        UUID volunteerId,
        UUID eventId,
        EventRole eventRole,
        OffsetDateTime workDate,
        int hoursWorked,
        String description
) {
}
