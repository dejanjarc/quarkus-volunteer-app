package si.rsj.pu.api.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import si.rsj.pu.api.dto.request.CreateVolunteerRequest;
import si.rsj.pu.api.dto.request.UpdateVolunteerRequest;
import si.rsj.pu.api.dto.response.VolunteerResponse;
import si.rsj.pu.api.mapper.VolunteerMapper;
import si.rsj.pu.api.rest.contract.VolunteerApi;
import si.rsj.pu.entity.Volunteer;
import si.rsj.pu.service.VolunteerService;

import java.net.URI;
import java.util.UUID;

@Path("/volunteers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerController implements VolunteerApi {

    @Inject
    VolunteerMapper volunteerMapper;

    @Inject
    VolunteerService volunteerService;

    private UUID parseUuid(String rawId) {
        try {
            return UUID.fromString(rawId);
        } catch (IllegalArgumentException e) {
            throw new si.rsj.pu.service.exception.common.ValidationException("Invalid UUID");
        }
    }

    @Override
    public VolunteerResponse getVolunteer(String id) {
        return volunteerMapper.toResponse(volunteerService.getVolunteer(parseUuid(id)));
    }

    @Override
    public Response createVolunteer(CreateVolunteerRequest request) {
        Volunteer created = volunteerService.createVolunteer(
                volunteerMapper.toCreateCommand(request)
        );

        VolunteerResponse response = volunteerMapper.toResponse(created);

        return Response.created(URI.create("/volunteers/" + response.id()))
                .entity(response)
                .build();
    }

    @Override
    public VolunteerResponse updateVolunteer(String id, UpdateVolunteerRequest request) {
        Volunteer updated = volunteerService.updateVolunteer(
                parseUuid(id),
                volunteerMapper.toUpdateCommand(request)
        );

        return volunteerMapper.toResponse(updated);
    }

    @Override
    public Response deleteVolunteer(String id) {
        volunteerService.deleteVolunteer(parseUuid(id));
        return Response.noContent().build();
    }
}