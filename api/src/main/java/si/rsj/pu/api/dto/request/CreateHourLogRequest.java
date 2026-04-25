package si.rsj.pu.api.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import si.rsj.pu.entity.enums.EventRole;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CreateHourLogRequest(
    @NotNull UUID volunteerId,
    @NotNull UUID eventId,
    @NotNull EventRole eventRole,
    @NotNull OffsetDateTime workDate,
    @Positive int hoursWorked,
    String description
){

}
