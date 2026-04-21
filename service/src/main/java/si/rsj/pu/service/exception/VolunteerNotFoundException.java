package si.rsj.pu.service.exception;

import java.util.UUID;

public class VolunteerNotFoundException extends RuntimeException {

    public VolunteerNotFoundException(UUID id){
        super("Volunteer not found:" + id);
    }
}
