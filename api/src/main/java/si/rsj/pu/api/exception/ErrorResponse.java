package si.rsj.pu.api.exception;

public record ErrorResponse(
        String error,
        String message
) {
}