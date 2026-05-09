package si.rsj.pu.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import si.rsj.pu.entity.Event;
import si.rsj.pu.repository.EventRepository;
import si.rsj.pu.repository.HourLogRepository;
import si.rsj.pu.service.command.CreateEventCommand;
import si.rsj.pu.service.command.UpdateEventCommand;
import si.rsj.pu.service.exception.event.EventNotFoundException;
import si.rsj.pu.service.exception.common.ValidationException;
import si.rsj.pu.service.exception.common.ConflictException;


import java.time.OffsetDateTime;
import java.util.UUID;

@ApplicationScoped
public class EventService {

    @Inject
    EventRepository eventRepository;

    @Inject
    HourLogRepository hourLogRepository;

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

    @Transactional
    public Event updateEvent(UUID id, UpdateEventCommand command) {
        Event event = eventRepository.findByIdOptional(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        if (command.name() != null && !command.name().isBlank()) {
            event.name = command.name();
        }

        if (command.description() != null) {
            event.description = command.description();
        }

        if (command.location() != null) {
            event.location = command.location();
        }

        OffsetDateTime newStartDate = command.startDate() != null ? command.startDate() : event.startDate;
        OffsetDateTime newEndDate = command.endDate() != null ? command.endDate() : event.endDate;

        if (newEndDate.isBefore(newStartDate)) {
            throw new ValidationException("endDate must not be before startDate");
        }

        event.startDate = newStartDate;
        event.endDate = newEndDate;

        return event;
    }

    @Transactional
    public void deleteEvent(UUID id) {
        Event event = eventRepository.findByIdOptional(id)
                .orElseThrow(() -> new EventNotFoundException(id));

        if (hourLogRepository.existsByEventId(id)) {
            throw new ConflictException("Event cannot be deleted because hour logs reference it");
        }

        eventRepository.delete(event);
    }

    public Event getEvent(UUID id) {
        return eventRepository.findByIdOptional(id)
                .orElseThrow(() -> new EventNotFoundException(id));
    }
}