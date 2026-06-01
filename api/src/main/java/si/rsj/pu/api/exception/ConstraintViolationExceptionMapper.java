package si.rsj.pu.api.exception;

import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    private static final Logger logger = Logger.getLogger(ConstraintViolationExceptionMapper.class);
    @Override
    public Response toResponse(ConstraintViolationException exception) {
        logger.warn("Constraint violation", exception);
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}