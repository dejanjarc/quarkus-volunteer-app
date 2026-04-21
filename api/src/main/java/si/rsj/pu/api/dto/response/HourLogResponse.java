package si.rsj.pu.api.dto.response;

import si.rsj.pu.model.enums.EventRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public record HourLogResponse(
    UUID id,
    UUID volunteerId,
    UUID eventId,
    EventRole eventRole,
    OffsetDateTime workDate,
    int hoursWorked,
    String description,
    OffsetDateTime submittedAt,
    String notes
){

}
