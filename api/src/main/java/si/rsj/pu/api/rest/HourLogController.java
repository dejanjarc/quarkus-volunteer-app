package si.rsj.pu.api.rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.rsj.pu.api.dto.request.CreateHourLogRequest;
import si.rsj.pu.api.dto.response.HourLogResponse;
import si.rsj.pu.entity.HourLog;
import si.rsj.pu.service.HourLogService;
import si.rsj.pu.service.command.CreateHourLogCommand;

import java.net.URI;
import java.util.UUID;

@Path("/hourlog")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HourLogController {

    @Inject
    HourLogService hourLogService;

    @POST
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
    public HourLogResponse getHourLog(@PathParam("id") UUID id) {
        return toResponse(hourLogService.getHourLog(id));
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