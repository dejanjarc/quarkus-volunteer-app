package si.rsj.pu.api.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger logger = LogManager.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        logger.warn("Unhandled exception", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }
}