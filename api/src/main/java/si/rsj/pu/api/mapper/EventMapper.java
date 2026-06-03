package si.rsj.pu.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import si.rsj.pu.api.dto.request.CreateEventRequest;
import si.rsj.pu.api.dto.request.UpdateEventRequest;
import si.rsj.pu.api.dto.response.EventResponse;
import si.rsj.pu.entity.Event;
import si.rsj.pu.service.command.CreateEventCommand;
import si.rsj.pu.service.command.UpdateEventCommand;

@Mapper(componentModel = "cdi", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EventMapper {

    CreateEventCommand toCreateCommand(CreateEventRequest request);

    UpdateEventCommand toUpdateCommand(UpdateEventRequest request);

    EventResponse toResponse(Event event);
}