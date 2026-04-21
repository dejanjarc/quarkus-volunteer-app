package si.rsj.pu.api.resource;

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
import si.rsj.pu.api.dto.request.CreateVolunteerRequest;
import si.rsj.pu.api.dto.response.VolunteerResponse;
import si.rsj.pu.entity.VolunteerEntity;
import si.rsj.pu.service.VolunteerService;
import si.rsj.pu.service.command.CreateVolunteerCommand;

import java.net.URI;
import java.util.UUID;

@Path("/volunteers")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class VolunteerResource {

    @Inject
    VolunteerService volunteerService;

    @POST
    public Response createVolunteer(@Valid CreateVolunteerRequest request) {
        VolunteerEntity created = volunteerService.createVolunteer(
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
    public VolunteerResponse getVolunteer(@PathParam("id") UUID id) {
        return toResponse(volunteerService.getVolunteer(id));
    }

    private VolunteerResponse toResponse(VolunteerEntity volunteer) {
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
}