package si.rsj.pu.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import si.rsj.pu.entity.Event;
import si.rsj.pu.entity.HourLog;
import si.rsj.pu.entity.Volunteer;
import si.rsj.pu.repository.EventRepository;
import si.rsj.pu.repository.HourLogRepository;
import si.rsj.pu.repository.VolunteerRepository;
import si.rsj.pu.service.HourLogService;
import si.rsj.pu.service.command.CreateHourLogCommand;
import si.rsj.pu.service.command.UpdateHourLogCommand;
import si.rsj.pu.service.exception.hourlog.HourLogNotFoundException;
import si.rsj.pu.service.exception.common.ValidationException;
import si.rsj.pu.service.exception.event.EventNotFoundException;
import si.rsj.pu.service.exception.volunteer.VolunteerNotFoundException;

import java.time.OffsetDateTime;
import java.util.UUID;

@ApplicationScoped
public class HourLogServiceImpl implements HourLogService {

    @Inject
    HourLogRepository hourLogRepository;

    @Inject
    VolunteerRepository volunteerRepository;

    @Inject
    EventRepository eventRepository;

    @Override
    public HourLog getHourLog(UUID id) {
        return hourLogRepository.findByIdOptional(id)
                .orElseThrow(() -> new HourLogNotFoundException(id));
    }

    @Override
    @Transactional
    public HourLog createHourLog(CreateHourLogCommand command) {
        Volunteer volunteer = volunteerRepository.findByIdOptional(command.volunteerId())
                .orElseThrow(() -> new VolunteerNotFoundException(command.volunteerId()));

        Event event = eventRepository.findByIdOptional(command.eventId())
                .orElseThrow(() -> new EventNotFoundException(command.eventId()));

        if (command.hoursWorked() <= 0) {
            throw new ValidationException("hoursWorked must be greater than 0");
        }

        if (command.workDate().isBefore(event.startDate) || command.workDate().isAfter(event.endDate)) {
            throw new ValidationException("workDate must be within the event time range");
        }

        HourLog hourLog = new HourLog();
        hourLog.id = UUID.randomUUID();
        hourLog.volunteer = volunteer;
        hourLog.event = event;
        hourLog.eventRole = command.eventRole();
        hourLog.workDate = command.workDate();
        hourLog.hoursWorked = command.hoursWorked();
        hourLog.description = command.description();
        hourLog.submittedAt = OffsetDateTime.now();

        hourLogRepository.persist(hourLog);
        return hourLog;
    }

    @Override
    @Transactional
    public HourLog updateHourLog(UUID id, UpdateHourLogCommand command) {
        HourLog hourLog = hourLogRepository.findByIdOptional(id)
                .orElseThrow(() -> new HourLogNotFoundException(id));

        Volunteer currentVolunteer = hourLog.volunteer;
        Event currentEvent = hourLog.event;

        if (command.volunteerId() != null) {
            currentVolunteer = volunteerRepository.findByIdOptional(command.volunteerId())
                    .orElseThrow(() -> new VolunteerNotFoundException(command.volunteerId()));
        }

        if (command.eventId() != null) {
            currentEvent = eventRepository.findByIdOptional(command.eventId())
                    .orElseThrow(() -> new EventNotFoundException(command.eventId()));
        }

        OffsetDateTime effectiveWorkDate = command.workDate() != null ? command.workDate() : hourLog.workDate;

        if (effectiveWorkDate.isBefore(currentEvent.startDate) || effectiveWorkDate.isAfter(currentEvent.endDate)) {
            throw new ValidationException("workDate must be within the event time range");
        }

        if (command.hoursWorked() != null && command.hoursWorked() <= 0) {
            throw new ValidationException("hoursWorked must be greater than 0");
        }

        hourLog.volunteer = currentVolunteer;
        hourLog.event = currentEvent;

        if (command.eventRole() != null) {
            hourLog.eventRole = command.eventRole();
        }

        hourLog.workDate = effectiveWorkDate;

        if (command.hoursWorked() != null) {
            hourLog.hoursWorked = command.hoursWorked();
        }

        if (command.description() != null) {
            hourLog.description = command.description();
        }

        return hourLog;
    }

    @Override
    @Transactional
    public void deleteHourLog(UUID id) {
        HourLog hourLog = hourLogRepository.findByIdOptional(id)
                .orElseThrow(() -> new HourLogNotFoundException(id));

        hourLogRepository.delete(hourLog);
    }
}
