package si.rsj.pu.api.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import si.rsj.pu.service.exception.VolunteerAlreadyExistsException;

import java.util.Map;

@Provider
public class VolunteerAlreadyExistsExceptionMapper implements ExceptionMapper<VolunteerAlreadyExistsException> {

    @Override
    public Response toResponse(VolunteerAlreadyExistsException exception) {
        return Response.status(Response.Status.CONFLICT)
                .type(MediaType.APPLICATION_JSON)
                .entity(Map.of(
                        "error", "Volunteer already exists",
                        "message", exception.getMessage()
                ))
                .build();
    }
}