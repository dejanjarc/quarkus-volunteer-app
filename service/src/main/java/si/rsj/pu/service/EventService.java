package si.rsj.pu.service;

import si.rsj.pu.entity.Event;
import si.rsj.pu.service.command.CreateEventCommand;
import si.rsj.pu.service.command.UpdateEventCommand;

import java.util.UUID;

/**
 * Service contract for managing events.
 */
public interface EventService {

    /**
     * Returns an event by ID.
     *
     * @param id event ID
     * @return event entity
     */
    Event getEvent(UUID id);

    /**
     * Creates a new event.
     *
     * @param command create event data
     * @return created event entity
     */
    Event createEvent(CreateEventCommand command);

    /**
     * Updates selected event fields.
     *
     * @param id event ID
     * @param command update data
     * @return updated event entity
     */
    Event updateEvent(UUID id, UpdateEventCommand command);

    /**
     * Deletes an event by ID.
     *
     * @param id event ID
     */
    void deleteEvent(UUID id);

}