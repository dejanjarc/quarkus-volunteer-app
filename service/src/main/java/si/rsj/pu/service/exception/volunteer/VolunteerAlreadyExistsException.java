package si.rsj.pu.service.exception.volunteer;

import si.rsj.pu.service.exception.common.ConflictException;

public class VolunteerAlreadyExistsException extends ConflictException {
    public VolunteerAlreadyExistsException(String message) {
        super(message);
    }
}