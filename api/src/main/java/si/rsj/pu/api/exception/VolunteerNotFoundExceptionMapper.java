package si.rsj.pu.api.exception;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import si.rsj.pu.service.exception.VolunteerNotFoundException;

import java.util.Map;

@Provider
public class VolunteerNotFoundExceptionMapper implements ExceptionMapper<VolunteerNotFoundException> {

    @Override
    public Response toResponse(VolunteerNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(Map.of(
                        "error", "Volunteer not found",
                        "message", exception.getMessage()
                ))
                .build();
    }
}