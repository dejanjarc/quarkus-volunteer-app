package si.rsj.pu.service;

import si.rsj.pu.entity.HourLog;
import si.rsj.pu.service.command.CreateHourLogCommand;
import si.rsj.pu.service.command.UpdateHourLogCommand;

import java.util.UUID;

/**
 * Service contract for managing hour logs.
 */
public interface HourLogService {

    /**
     * Returns an hour log by ID.
     *
     * @param id hour log ID
     * @return hour log entity
     */
    HourLog getHourLog(UUID id);

    /**
     * Creates a new hour log.
     *
     * @param command create hour log data
     * @return created hour log entity
     */
    HourLog createHourLog(CreateHourLogCommand command);

    /**
     * Updates selected hour log fields.
     *
     * @param id hour log ID
     * @param command update data
     * @return updated hour log entity
     */
    HourLog updateHourLog(UUID id, UpdateHourLogCommand command);

    /**
     * Deletes an hour log by ID.
     *
     * @param id hour log ID
     */
    void deleteHourLog(UUID id);

}