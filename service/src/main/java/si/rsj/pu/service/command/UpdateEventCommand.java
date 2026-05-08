package si.rsj.pu.service.command;

import java.time.OffsetDateTime;

public record UpdateEventCommand(
    String name,
    String description,
    String location,
    OffsetDateTime startDate,
    OffsetDateTime endDate
){

}