package si.rsj.pu.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import si.rsj.pu.entity.Volunteer;
import si.rsj.pu.repository.HourLogRepository;
import si.rsj.pu.repository.VolunteerRepository;
import si.rsj.pu.service.command.CreateVolunteerCommand;
import si.rsj.pu.service.command.UpdateVolunteerCommand;
import si.rsj.pu.service.exception.common.ValidationException;
import si.rsj.pu.service.exception.common.ConflictException;
import si.rsj.pu.service.exception.volunteer.VolunteerAlreadyExistsException;
import si.rsj.pu.service.exception.volunteer.VolunteerNotFoundException;


import java.time.OffsetDateTime;
import java.util.UUID;

@ApplicationScoped
public class VolunteerService {

    @Inject
    VolunteerRepository volunteerRepository;

    @Inject
    HourLogRepository hourLogRepository;

    @Transactional
    public Volunteer createVolunteer(CreateVolunteerCommand command) {
        volunteerRepository.findByZtsCode(command.ztsCode())
                .ifPresent(v -> {
                    throw new VolunteerAlreadyExistsException(
                            "Volunteer with ztsCode " + command.ztsCode() + " already exists"
                    );
                });

        if (command.email() != null && !command.email().isBlank()) {
            volunteerRepository.findByEmail(command.email())
                    .ifPresent(v -> {
                        throw new VolunteerAlreadyExistsException(
                                "Volunteer with email " + command.email() + " already exists"
                        );
                    });
        }

        Volunteer volunteer = new Volunteer();
        volunteer.id = UUID.randomUUID();
        volunteer.ztsCode = command.ztsCode();
        volunteer.firstName = command.firstName();
        volunteer.lastName = command.lastName();
        volunteer.volunteerRole = command.volunteerRole();
        volunteer.phoneNumber = command.phoneNumber();
        volunteer.email = command.email();
        volunteer.active = true;
        volunteer.joinedAt = OffsetDateTime.now();

        volunteerRepository.persist(volunteer);
        return volunteer;
    }

    @Transactional
    public Volunteer updateVolunteer(UUID id, UpdateVolunteerCommand command) {
        Volunteer volunteer = volunteerRepository.findByIdOptional(id)
                .orElseThrow(() -> new VolunteerNotFoundException(id));

        if (command.ztsCode() != null) {
            volunteer.ztsCode = command.ztsCode();
        }

        if (command.firstName() != null) {
            if (command.firstName().isBlank()) {
                throw new ValidationException("firstName must not be blank");
            }
            volunteer.firstName = command.firstName();
        }

        if (command.lastName() != null) {
            if (command.lastName().isBlank()) {
                throw new ValidationException("lastName must not be blank");
            }
            volunteer.lastName = command.lastName();
        }

        if (command.volunteerRole() != null) {
            volunteer.volunteerRole = command.volunteerRole();
        }

        if (command.phoneNumber() != null) {
            volunteer.phoneNumber = command.phoneNumber();
        }

        if (command.email() != null) {
            volunteer.email = command.email();
        }

        if (command.active() != null) {
            volunteer.active = command.active();
        }

        return volunteer;
    }

    @Transactional
    public void deleteVolunteer(UUID id) {
        Volunteer volunteer = volunteerRepository.findByIdOptional(id)
                .orElseThrow(() -> new VolunteerNotFoundException(id));

        if (hourLogRepository.existsByVolunteerId(id)) {
            throw new ConflictException("Volunteer cannot be deleted because hour logs reference it");
        }

        volunteerRepository.delete(volunteer);
    }

    public Volunteer getVolunteer(UUID id) {
        return volunteerRepository.findByIdOptional(id)
                .orElseThrow(() -> new VolunteerNotFoundException(id));
    }
}