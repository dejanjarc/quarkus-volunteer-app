package si.rsj.pu.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import si.rsj.pu.entity.Event;
import si.rsj.pu.repository.EventRepository;
import si.rsj.pu.service.command.CreateEventCommand;
import si.rsj.pu.service.exception.event.EventNotFoundException;
import si.rsj.pu.service.exception.common.ValidationException;

import java.time.OffsetDateTime;
import java.util.UUID;

@ApplicationScoped
public class EventService {

    @Inject
    EventRepository eventRepository;

    @Transactional
    public Event createEvent(CreateEventCommand command) {
        if (command.endDate().isBefore(command.startDate())) {
            throw new ValidationException("endDate must not be before startDate");
        }

        Event event = new Event();
        event.id = UUID.randomUUID();
        event.name = command.name();
        event.description = command.description();
        event.location = command.location();
        event.startDate = command.startDate();
        event.endDate = command.endDate();
        event.createdAt = OffsetDateTime.now();

        eventRepository.persist(event);
        return event;
    }

    public Event getEvent(UUID id) {
        return eventRepository.findByIdOptional(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}