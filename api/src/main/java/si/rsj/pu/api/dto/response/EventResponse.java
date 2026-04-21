package si.rsj.pu.api.dto.response;

import java.time.OffsetDateTime;
import java.util.UUID;

public record EventResponse(
    UUID id,
    String name,
    String description,
    String location,
    OffsetDateTime startDate,
    OffsetDateTime endDate,
    OffsetDateTime createdAt
) {

}
