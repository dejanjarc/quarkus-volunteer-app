package si.rsj.pu.api.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.rsj.pu.api.dto.request.CreateHourLogRequest;
import si.rsj.pu.api.dto.request.UpdateHourLogRequest;
import si.rsj.pu.api.dto.response.HourLogResponse;
import si.rsj.pu.api.mapper.HourLogMapper;
import si.rsj.pu.api.rest.contract.HourLogApi;
import si.rsj.pu.entity.HourLog;
import si.rsj.pu.service.HourLogService;

import java.net.URI;
import java.util.UUID;

@Path("/hourlog")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HourLogController implements HourLogApi {

    @Inject
    HourLogMapper hourLogMapper;

    @Inject
    HourLogService hourLogService;

    private UUID parseUuid(String rawId) {
        try {
            return UUID.fromString(rawId);
        } catch (IllegalArgumentException e) {
            throw new si.rsj.pu.service.exception.common.ValidationException("Invalid UUID");
        }
    }

    @Override
    public HourLogResponse getHourLog(String id) {
        return hourLogMapper.toResponse(hourLogService.getHourLog(parseUuid(id)));
    }

    @Override
    public Response createHourLog(CreateHourLogRequest request) {
        HourLog created = hourLogService.createHourLog(
                hourLogMapper.toCreateCommand(request)
        );

        HourLogResponse response = hourLogMapper.toResponse(created);

        return Response.created(URI.create("/hourlog/" + response.id()))
                .entity(response)
                .build();
    }

    @Override
    public HourLogResponse updateHourLog(String id, UpdateHourLogRequest request) {
        HourLog updated = hourLogService.updateHourLog(
                parseUuid(id),
                hourLogMapper.toUpdateCommand(request)
        );

        return hourLogMapper.toResponse(updated);
    }

    @Override
    public Response deleteHourLog(String id) {
        hourLogService.deleteHourLog(parseUuid(id));
        return Response.noContent().build();
    }
}