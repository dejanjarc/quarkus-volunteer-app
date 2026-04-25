package si.rsj.pu.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import si.rsj.pu.entity.Volunteer;
import si.rsj.pu.repository.VolunteerRepository;
import si.rsj.pu.service.command.CreateVolunteerCommand;
import si.rsj.pu.service.exception.volunteer.VolunteerAlreadyExistsException;
import si.rsj.pu.service.exception.volunteer.VolunteerNotFoundException;

import java.time.OffsetDateTime;
import java.util.UUID;

@ApplicationScoped
public class VolunteerService {

    @Inject
    VolunteerRepository volunteerRepository;

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

    public Volunteer getVolunteer(UUID id) {
        return volunteerRepository.findByIdOptional(id)
                .orElseThrow(() -> new VolunteerNotFoundException(id));
    }
}