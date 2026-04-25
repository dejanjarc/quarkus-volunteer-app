package si.rsj.pu.service.exception.volunteer;

import si.rsj.pu.service.exception.common.NotFoundException;

import java.util.UUID;

public class VolunteerNotFoundException extends NotFoundException {
    public VolunteerNotFoundException(UUID id) {
        super("Volunteer not found: " + id);
    }
}
