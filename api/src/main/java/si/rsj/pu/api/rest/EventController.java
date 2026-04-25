package si.rsj.pu.api.rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.rsj.pu.api.dto.request.CreateEventRequest;
import si.rsj.pu.api.dto.response.EventResponse;
import si.rsj.pu.entity.Event;
import si.rsj.pu.service.EventService;
import si.rsj.pu.service.command.CreateEventCommand;

import java.net.URI;
import java.util.UUID;

@Path("/events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EventController {

    @Inject
    EventService eventService;

    @POST
    public Response createEvent(@Valid CreateEventRequest request) {
        Event created = eventService.createEvent(
                new CreateEventCommand(
                        request.name(),
                        request.description(),
                        request.location(),
                        request.startDate(),
                        request.endDate()
                )
        );

        EventResponse response = toResponse(created);

        return Response.created(URI.create("/events/" + response.id()))
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    public EventResponse getEvent(@PathParam("id") UUID id) {
        return toResponse(eventService.getEvent(id));
    }

    private EventResponse toResponse(Event event) {
        return new EventResponse(
                event.id,
                event.name,
                event.description,
                event.location,
                event.startDate,
                event.endDate,
                event.createdAt
        );
    }
}