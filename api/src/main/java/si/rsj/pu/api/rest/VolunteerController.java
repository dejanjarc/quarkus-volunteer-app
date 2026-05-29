package si.rsj.pu.api.rest;

import jakarta.inject.Inject;
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
import si.rsj.pu.api.dto.request.CreateVolunteerRequest;
import si.rsj.pu.api.dto.request.UpdateVolunteerRequest;
import si.rsj.pu.api.dto.response.VolunteerResponse;
import si.rsj.pu.api.exception.ErrorResponse;
import si.rsj.pu.entity.Volunteer;
import si.rsj.pu.service.VolunteerService;
import si.rsj.pu.service.command.CreateVolunteerCommand;
import si.rsj.pu.service.command.UpdateVolunteerCommand;

import java.net.URI;
import java.util.UUID;

@Path("/volunteers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerController {

    @Inject
    VolunteerService volunteerService;

    @POST
    @Operation(
            summary = "Create a volunteer",
            description = "Creates a new volunteer and returns the created volunteer"
    )
    @RequestBody(
            description = "Volunteer creation body",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = CreateVolunteerRequest.class),
                    examples = @ExampleObject(
                            name = "CreateVolunteerExample",
                            value = """
                                    {
                                      "ztsCode": 51293,
                                      "firstName": "Janez",
                                      "lastName": "Novak",
                                      "volunteerRole": "STARESINA",
                                      "phoneNumber": "+386 40 123 456",
                                      "email": "janez.novak@gmail.com"
                                    }
                                    """
                    )
            )
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "Volunteer created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VolunteerResponse.class),
                            examples = @ExampleObject(
                                    name = "CreatedVolunteerExample",
                                    value = """
                                            {
                                              "id": "eb6715c1-5ab4-412f-12a0-7d17ec71aa13",
                                              "ztsCode": 51293,
                                              "firstName": "Janez",
                                              "lastName": "Novak",
                                              "volunteerRole": "STARESINA",
                                              "phoneNumber": "+386 40 123 456",
                                              "email": "janez.novak@gmail.com",
                                              "active": true,
                                              "joinedAt": "2026-05-01T16:35:30+02:00"
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
    public Response createVolunteer(@Valid CreateVolunteerRequest request) {
        Volunteer created = volunteerService.createVolunteer(
                new CreateVolunteerCommand(
                        request.ztsCode(),
                        request.firstName(),
                        request.lastName(),
                        request.volunteerRole(),
                        request.phoneNumber(),
                        request.email()
                )
        );

        VolunteerResponse response = toResponse(created);

        return Response.created(URI.create("/volunteers/" + response.id()))
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get a volunteer",
            description = "Gets a volunteer by ID"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Volunteer found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VolunteerResponse.class),
                            examples = @ExampleObject(
                                name = "GetVolunteerExample",
                                value = """
                                        { "id": "eb6715c1-5ab4-412f-12a0-7d17ec71aa13",
                                          "ztsCode": 51293,
                                          "firstName": "Janez",
                                          "lastName": "Novak",
                                          "volunteerRole": "STARESINA",
                                          "phoneNumber": "+386 40 123 456",
                                          "email": "janez.novak@gmail.com",
                                          "active": true,
                                          "joinedAt": "2026-05-01T16:35:30+02:00"
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
                    description = "Volunteer not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class),
                            example =
                                    """
                                    {
                                      "error": "Not Found",
                                      "message": "Volunteer not found: d290f1ee-6c54-4b01-90e6-d701748f0851"
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
    public VolunteerResponse getVolunteer(@PathParam("id") String id) {
        return toResponse(volunteerService.getVolunteer(parseUuid(id)));
    }

    private UUID parseUuid(String rawId) {
        try {
            return UUID.fromString(rawId);
        } catch (IllegalArgumentException e) {
            throw new si.rsj.pu.service.exception.common.ValidationException("Invalid UUID");
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete a volunteer",
            description = "Deletes a volunteer by ID."
    )
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Volunteer deleted successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "400", description = "Invalid volunteer ID",
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
                    description = "Volunteer not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class),
                            example =
                                """
                                {
                                  "error": "Not Found",
                                  "message": "Volunteer not found: d290f1ee-6c54-4b01-90e6-d701748f0851"
                                }
                                """
                    )
            ),
            @APIResponse(
                    responseCode = "409",
                    description = "Volunteer cannot be deleted because it is referenced",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @APIResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            ),
            @APIResponse(
                    responseCode = "503",
                    description = "Service unavailable",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )
            )
    })
    public Response deleteVolunteer(@PathParam("id") UUID id) {
        volunteerService.deleteVolunteer(id);
        return Response.noContent().build();
    }

    private VolunteerResponse toResponse(Volunteer volunteer) {
        return new VolunteerResponse(
                volunteer.id,
                volunteer.ztsCode,
                volunteer.firstName,
                volunteer.lastName,
                volunteer.volunteerRole,
                volunteer.phoneNumber,
                volunteer.email,
                volunteer.active,
                volunteer.joinedAt
        );
    }

    @PATCH
    @Path("/{id}")
    @Operation(
            summary = "Update a Volunteer",
            description = "Update a Volunteer by ID for selected fields (PATCH)"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Volunteer updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = VolunteerResponse.class),
                            examples = @ExampleObject(
                                    name = "UpdatedVolunteerExample",
                                    value = """
                                        { "id": "eb6715c1-5ab4-412f-12a0-7d17ec71aa13",
                                          "ztsCode": 77219,
                                          "firstName": "Janez",
                                          "lastName": "Novak",
                                          "volunteerRole": "NACELNIK",
                                          "phoneNumber": "+386 40 456 789",
                                          "email": "janez.novak123@gmail.com",
                                          "active": true,
                                          "joinedAt": "2026-05-01T16:35:30+02:00"
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
                    description = "Volunteer not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class),
                            example =
                                    """
                                    {
                                      "error": "Not Found",
                                      "message": "Volunteer not found: d290f1ee-6c54-4b01-90e6-d701748f0851"
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
    public VolunteerResponse updateVolunteer(@PathParam("id") UUID id, @Valid UpdateVolunteerRequest request) {
        Volunteer updated = volunteerService.updateVolunteer(
                id,
                new UpdateVolunteerCommand(
                        request.ztsCode(),
                        request.firstName(),
                        request.lastName(),
                        request.volunteerRole(),
                        request.phoneNumber(),
                        request.email(),
                        request.active()
                )
        );

        return toResponse(updated);
    }
}