package si.rsj.pu.api.rest.contract;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
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
import si.rsj.pu.api.dto.request.UpdateEventRequest;
import si.rsj.pu.api.dto.response.EventResponse;
import si.rsj.pu.api.exception.ErrorResponse;

@Path("/events")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface EventApi {

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
                            mediaType = MediaType.APPLICATION_JSON,
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
            @APIResponse(responseCode = "401", description = "Authentication required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
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
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    EventResponse getEvent(@PathParam("id") String id);

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
            @APIResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "401", description = "Authentication required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "409", description = "Conflict with existing resource",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))

    })
    Response createEvent(@Valid CreateEventRequest request);

    @PATCH
    @Path("/{id}")
    @Operation(
            summary = "Update an event",
            description = "Update an event by ID for selected fields (PATCH)"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Event updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = EventResponse.class),
                            examples = @ExampleObject(
                                    name = "UpdatedEventExample",
                                    value = """
                                        {
                                          "id": "4deb8621-b7da-48fb-be83-b188ba1562ec",
                                          "name": "Tabor 2026 - updated",
                                          "description": "Poletni tabor 2026 pri Podgozdu",
                                          "location": "Podgozd",
                                          "startDate": "2026-07-03T08:00:00Z",
                                          "endDate": "2026-07-12T08:00:00Z",
                                          "createdAt": "2026-05-07T08:04:26.960542Z"
                                        }
                                        """
                            )
                    )
            ),
            @APIResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "401", description = "Authentication required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
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
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    EventResponse updateEvent(@PathParam("id") String id, @Valid UpdateEventRequest request);

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete an event",
            description = "Deletes an event by ID."
    )
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Event deleted successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "400", description = "Invalid event ID",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "401", description = "Authentication required",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "403", description = "Forbidden",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(
                    responseCode = "404",
                    description = "Event not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
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
            @APIResponse(responseCode = "409", description = "Event cannot be deleted because it is referenced",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "503", description = "Service unavailable",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    ))
    })
    Response deleteEvent(@PathParam("id") String id);

}