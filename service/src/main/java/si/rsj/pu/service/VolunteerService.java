package si.rsj.pu.service;

import si.rsj.pu.entity.Volunteer;
import si.rsj.pu.service.command.CreateVolunteerCommand;
import si.rsj.pu.service.command.UpdateVolunteerCommand;

import java.util.UUID;

/**
 * Service contract for managing volunteers.
 */
public interface VolunteerService {

    /**
     * Returns a volunteer by ID.
     *
     * @param id volunteer ID
     * @return volunteer entity
     */
    Volunteer getVolunteer(UUID id);

    /**
     * Creates a new volunteer.
     *
     * @param command create volunteer data
     * @return created volunteer entity
     */
    Volunteer createVolunteer(CreateVolunteerCommand command);

    /**
     * Updates selected volunteer fields.
     *
     * @param id volunteer ID
     * @param command update data
     * @return updated volunteer entity
     */
    Volunteer updateVolunteer(UUID id, UpdateVolunteerCommand command);

    /**
     * Deletes a volunteer by ID.
     *
     * @param id volunteer ID
     */
    void deleteVolunteer(UUID id);

}