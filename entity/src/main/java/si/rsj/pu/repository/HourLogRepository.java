package si.rsj.pu.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import si.rsj.pu.entity.HourLog;

import java.util.UUID;

@ApplicationScoped
public class HourLogRepository implements PanacheRepositoryBase<HourLog, UUID> {
    public boolean existsByVolunteerId(UUID volunteerId) {
        return count("volunteer.id", volunteerId) > 0;
    }

    public boolean existsByEventId(UUID eventId) {
        return count("event.id", eventId) > 0;
    }
}
