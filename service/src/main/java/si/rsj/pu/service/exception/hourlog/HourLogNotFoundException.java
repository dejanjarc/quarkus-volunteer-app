package si.rsj.pu.service.exception.hourlog;

import si.rsj.pu.service.exception.common.NotFoundException;

import java.util.UUID;

public class HourLogNotFoundException extends NotFoundException {
    public HourLogNotFoundException(UUID id) {
        super("HourLog not found: " + id);
    }
}