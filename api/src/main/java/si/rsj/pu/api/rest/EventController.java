package si.rsj.pu.api.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.rsj.pu.api.dto.request.CreateEventRequest;
import si.rsj.pu.api.dto.request.UpdateEventRequest;
import si.rsj.pu.api.dto.response.EventResponse;
import si.rsj.pu.api.mapper.EventMapper;
import si.rsj.pu.api.rest.contract.EventApi;
import si.rsj.pu.entity.Event;
import si.rsj.pu.service.EventService;

import java.net.URI;
import java.util.UUID;

@Path("/events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventController implements EventApi {

    @Inject
    EventMapper eventMapper;

    @Inject
    EventService eventService;

    private UUID parseUuid(String rawId) {
        try {
            return UUID.fromString(rawId);
        } catch (IllegalArgumentException e) {
            throw new si.rsj.pu.service.exception.common.ValidationException("Invalid UUID");
        }
    }

    @Override
    public EventResponse getEvent(String id) {
        return eventMapper.toResponse(eventService.getEvent(parseUuid(id)));
    }

    @Override
    public Response createEvent(CreateEventRequest request) {
        Event created = eventService.createEvent(
                eventMapper.toCreateCommand(request)
        );

        EventResponse response = eventMapper.toResponse(created);

        return Response.created(URI.create("/events/" + response.id()))
                .entity(response)
                .build();
    }

    @Override
    public EventResponse updateEvent(String id, UpdateEventRequest request) {
        Event updated = eventService.updateEvent(
                parseUuid(id),
                eventMapper.toUpdateCommand(request)
        );

        return eventMapper.toResponse(updated);
    }

    @Override
    public Response deleteEvent(String id) {
        eventService.deleteEvent(parseUuid(id));
        return Response.noContent().build();
    }

}