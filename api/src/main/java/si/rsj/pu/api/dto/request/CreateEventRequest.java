package si.rsj.pu.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record CreateEventRequest(
    @NotBlank String name,
    String description,
    String location,
    @NotNull OffsetDateTime startDate,
    @NotNull OffsetDateTime endDate
) {

}
