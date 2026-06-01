package si.rsj.pu.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import si.rsj.pu.service.exception.common.NotFoundException;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {

    private static final Logger logger = Logger.getLogger(NotFoundExceptionMapper.class);
    @Override
    public Response toResponse(NotFoundException exception) {
        logger.warn("Not Found", exception);
        return Response.status(Response.Status.NOT_FOUND).build();
    }
}