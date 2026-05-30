package si.rsj.pu.api.dto.request;

import si.rsj.pu.api.validation.HasEventDates;
import si.rsj.pu.api.validation.ValidEventDates;

import java.time.OffsetDateTime;

@ValidEventDates
public record UpdateEventRequest(
        String name,
        String description,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) implements HasEventDates {
}