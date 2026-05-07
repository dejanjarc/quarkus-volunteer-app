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
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.ExampleObject;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.rsj.pu.api.dto.request.CreateEventRequest;
import si.rsj.pu.api.dto.response.EventResponse;
import si.rsj.pu.api.exception.ErrorResponse;
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
    @Operation(
            summary = "Create an event",
            description = "Creates a new event and returns the created event"
    )
    @RequestBody(
            description = "Event creation body",
            content = @Content(
                    schema = @Schema(implementation = CreateEventRequest.class),
                    examples = @ExampleObject(
                            name = "CreateEventExample",
                            value = """
                                    {
                                        "name": "Tabor 2026",
                                        "description": "Poletni tabor 2026 pri Podgozdu",
                                        "location": "Podgozd",
                                        "startDate": "2026-07-03T10:00:00+02:00",
                                        "endDate": "2026-07-12T10:00:00+02:00"
                                    }
                                    """
                    )
            )
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Event created successfully",
                    content = @Content(
                            schema = @Schema(implementation = EventResponse.class),
                            examples = @ExampleObject(
                                    name = "CreatedEventExample",
                                    value = """
                                            {
                                                "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
                                                "name": "Tabor 2026",
                                                "description": "Poletni tabor 2026 pri Podgozdu",
                                                "location": "Podgozd",
                                                "startDate": "2026-07-03T10:00:00+02:00",
                                                "endDate": "2026-07-12T10:00:00+02:00",
                                                "createdAt": "2026-05-23T17:15:36+02:00"
                                            }
                                            """
                            )
                    )
            ),
            @APIResponse(responseCode = "400", description = "Invalid request payload"),
            @APIResponse(responseCode = "401", description = "Authentication required"),
            @APIResponse(responseCode = "403", description = "Forbidden"),
            @APIResponse(responseCode = "409", description = "Conflict with existing resource"),
            @APIResponse(responseCode = "500", description = "Internal server error"),
            @APIResponse(responseCode = "503", description = "Service unavailable")

    })
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
    @Operation(
            summary = "Get an event",
            description = "Gets an event by ID"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Event found",
                    content = @Content(
                            schema = @Schema(implementation = EventResponse.class),
                            examples = @ExampleObject(
                                    name = "GetEventExample",
                                    value = """
                                            {
                                                "id": "d290f1ee-6c54-4b01-90e6-d701748f0851",
                                                "name": "Tabor 2026",
                                                "description": "Poletni tabor 2026 pri Podgozdu",
                                                "location": "Podgozd",
                                                "startDate": "2026-07-03T10:00:00+02:00",
                                                "endDate": "2026-07-12T10:00:00+02:00",
                                                "createdAt": "2026-05-23T17:15:36+02:00"
                                            }
                                            """
                            )
                    )
            ),
            @APIResponse(responseCode = "401", description = "Authentication required"),
            @APIResponse(responseCode = "403", description = "Forbidden"),
            @APIResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            example =
                                    """
                                    {
                                      "error": "Not Found",
                                      "message": "Event not found: d290f1ee-6c54-4b01-90e6-d701748f0851"
                                    }
                                    """
                    )
            ),
            @APIResponse(responseCode = "500", description = "Internal server error"),
            @APIResponse(responseCode = "503", description = "Service unavailable")
    })
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