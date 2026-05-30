package si.rsj.pu.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import si.rsj.pu.api.validation.HasEventDates;
import si.rsj.pu.api.validation.ValidEventDates;

import java.time.OffsetDateTime;

@ValidEventDates
public record CreateEventRequest(
    @NotBlank String name,
    String description,
    String location,
    @NotNull OffsetDateTime startDate,
    @NotNull OffsetDateTime endDate
) implements HasEventDates {

}
