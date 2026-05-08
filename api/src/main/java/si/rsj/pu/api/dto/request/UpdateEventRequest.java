package si.rsj.pu.api.dto.request;

import java.time.OffsetDateTime;

public record UpdateEventRequest(
        String name,
        String description,
        String location,
        OffsetDateTime startDate,
        OffsetDateTime endDate
) {
}