package si.rsj.pu.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import si.rsj.pu.service.exception.common.ConflictException;

@Provider
public class ConflictExceptionMapper implements ExceptionMapper<ConflictException> {

    private static final Logger logger = Logger.getLogger(ConflictExceptionMapper.class);
    @Override
    public Response toResponse(ConflictException exception) {
        logger.warn("Conflict", exception);
        return Response.status(Response.Status.CONFLICT).build();
    }
}