package si.rsj.pu.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import si.rsj.pu.service.exception.common.ValidationException;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {

    private static final Logger logger = LogManager.getLogger(ValidationExceptionMapper.class);

    @Override
    public Response toResponse(ValidationException exception) {
        logger.warn("Validation error", exception);
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}