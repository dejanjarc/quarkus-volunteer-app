package si.rsj.pu.service.command;

import java.time.OffsetDateTime;

public record CreateEventCommand(
        String name,
        String description,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}