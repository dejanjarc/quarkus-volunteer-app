package si.rsj.pu.service.exception;

public class VolunteerAlreadyExistsException extends RuntimeException {
    public VolunteerAlreadyExistsException(String message) {
        super(message);
    }
}