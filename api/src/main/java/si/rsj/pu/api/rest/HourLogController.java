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
import si.rsj.pu.api.dto.request.CreateHourLogRequest;
import si.rsj.pu.api.dto.request.UpdateHourLogRequest;
import si.rsj.pu.api.dto.response.HourLogResponse;
import si.rsj.pu.api.exception.ErrorResponse;
import si.rsj.pu.entity.HourLog;
import si.rsj.pu.service.HourLogService;
import si.rsj.pu.service.command.CreateHourLogCommand;
import si.rsj.pu.service.command.UpdateHourLogCommand;

import java.net.URI;
import java.util.UUID;

@Path("/hourlog")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HourLogController {

    @Inject
    HourLogService hourLogService;

    @POST
    @Operation(
            summary = "Create an hour log",
            description = "Creates an hour log for a volunteer who was at an event and returns the created hour log"
    )
    @RequestBody(
            description = "HourLog creation body",
            content = @Content(
                    schema = @Schema(implementation = CreateHourLogRequest.class),
                    examples = @ExampleObject(
                            name = "CreateHourLogExample",
                            value = """
                                    {
                                      "volunteerId": "B804DAee-D8b1-8b2c-6BF7-e5Fbe4FEA67C",
                                      "eventId": "eeB67A62-4De9-D69b-b1DC-17D11D6d9a98",
                                      "eventRole": "STARESINA",
                                      "workDate": "2026-07-14T10:00:00+02:00",
                                      "hoursWorked": 8,
                                      "description": "Opravljeno delo starešine na akciji za 14.7.2026."
                                    }
                                    """
                    )
            )
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "201",
                    description = "HourLog created successfully",
                    content = @Content(
                            schema = @Schema(implementation = HourLogResponse.class),
                            examples = @ExampleObject(
                                    name = "CreatedHourLogExample",
                                    value = """
                                            {
                                              "id": "d79bF777-bF2f-584B-dCAc-E5FC26cfdEeb",
                                              "volunteerId": "B804DAee-D8b1-8b2c-6BF7-e5Fbe4FEA67C",
                                              "eventId": "eeB67A62-4De9-D69b-b1DC-17D11D6d9a98",
                                              "eventRole": "STARESINA",
                                              "workDate": "2026-07-14T10:00:00+02:00",
                                              "hoursWorked": 8,
                                              "description": "Opravljeno delo starešine na akciji za 14.7.2026.",
                                              "submittedAt": "2026-07-14T23:20:55+02:00"
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
    public Response createHourLog(@Valid CreateHourLogRequest request) {
        HourLog created = hourLogService.createHourLog(
                new CreateHourLogCommand(
                        request.volunteerId(),
                        request.eventId(),
                        request.eventRole(),
                        request.workDate(),
                        request.hoursWorked(),
                        request.description()
                )
        );

        HourLogResponse response = toResponse(created);

        return Response.created(URI.create("/hourlog/" + response.id()))
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}")
    @Operation(
            summary = "Get an hour log",
            description = "Gets an hour log by ID"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "HourLog found",
                    content = @Content(
                            schema = @Schema(implementation = HourLogResponse.class),
                            examples = @ExampleObject(
                                    name = "GetHourLogExample",
                                    value = """
                                            {
                                              "id": "d79bF777-bF2f-584B-dCAc-E5FC26cfdEeb",
                                              "volunteerId": "B804DAee-D8b1-8b2c-6BF7-e5Fbe4FEA67C",
                                              "eventId": "eeB67A62-4De9-D69b-b1DC-17D11D6d9a98",
                                              "eventRole": "STARESINA",
                                              "workDate": "2026-07-14T10:00:00+02:00",
                                              "hoursWorked": 8,
                                              "description": "Opravljeno delo starešine na akciji za 14.7.2026.",
                                              "submittedAt": "2026-07-14T23:20:55+02:00"
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
                    description = "Hour log not found",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            example =
                                    """
                                    {
                                      "error": "Not Found",
                                      "message": "HourLog not found: d290f1ee-6c54-4b01-90e6-d701748f0851"
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
    public HourLogResponse getHourLog(@PathParam("id") UUID id) {
        return toResponse(hourLogService.getHourLog(id));
    }

    @PATCH
    @Path("/{id}")
    @Operation(
        summary = "Update an hour log",
        description = "Update an hour log by ID for selected fields (PATCH)"
    )
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Hour log updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = HourLogResponse.class),
                            example =
                                """
                                {
                                  "id": "d79bF777-bF2f-584B-dCAc-E5FC26cfdEeb",
                                  "volunteerId": "B804DAee-D8b1-8b2c-6BF7-e5Fbe4FEA67C",
                                  "eventId": "eeB67A62-4De9-D69b-b1DC-17D11D6d9a98",
                                  "eventRole": "STARESINA",
                                  "workDate": "2026-07-14T10:00:00+02:00",
                                  "hoursWorked": 8,
                                  "description": "Opravljeno delo starešine na akciji za 14.7.2026.",
                                  "submittedAt": "2026-07-14T23:20:55+02:00"
                                }
                                """
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
            @APIResponse(responseCode = "404", description = "Hour log, volunteer, or event not found",
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
    public HourLogResponse updateHourLog(@PathParam("id") UUID id, @Valid UpdateHourLogRequest request) {
        HourLog updated = hourLogService.updateHourLog(
                id,
                new UpdateHourLogCommand(
                        request.volunteerId(),
                        request.eventId(),
                        request.eventRole(),
                        request.workDate(),
                        request.hoursWorked(),
                        request.description()
                )
        );

        return toResponse(updated);
    }

    @DELETE
    @Path("/{id}")
    @Operation(
            summary = "Delete an hour log",
            description = "Deletes an hour log by ID."
    )
    @APIResponses({
            @APIResponse(responseCode = "204", description = "HourLog deleted successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = ErrorResponse.class)
                    )),
            @APIResponse(responseCode = "400", description = "Invalid hour log ID",
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
                    description = "HourLog not found",
                    content = @Content(
                        mediaType = MediaType.APPLICATION_JSON,
                        schema = @Schema(implementation = ErrorResponse.class),
                            example =
                                """
                                {
                                  "error": "Not Found",
                                  "message": "HourLog not found: d290f1ee-6c54-4b01-90e6-d701748f0851"
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
    public Response deleteHourLog(@PathParam("id") UUID id) {
        hourLogService.deleteHourLog(id);
        return Response.noContent().build();
    }

    private HourLogResponse toResponse(HourLog hourLog) {
        return new HourLogResponse(
                hourLog.id,
                hourLog.volunteer.id,
                hourLog.event.id,
                hourLog.eventRole,
                hourLog.workDate,
                hourLog.hoursWorked,
                hourLog.description,
                hourLog.submittedAt
        );
    }
}