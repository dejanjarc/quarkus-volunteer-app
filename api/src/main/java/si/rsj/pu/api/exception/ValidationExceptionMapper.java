package si.rsj.pu.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import si.rsj.pu.service.exception.common.ValidationException;
import org.jboss.logging.Logger;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger logger = Logger.getLogger(ValidationExceptionMapper.class);
    @Override
    public Response toResponse(ValidationException exception) {
        logger.warn("Validation error", exception);
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}