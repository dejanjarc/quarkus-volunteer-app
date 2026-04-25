package si.rsj.pu.service.exception.event;

import si.rsj.pu.service.exception.common.NotFoundException;

import java.util.UUID;

public class EventNotFoundException extends NotFoundException {
    public EventNotFoundException(UUID id) {
        super("Event not found: " + id);
    }
}