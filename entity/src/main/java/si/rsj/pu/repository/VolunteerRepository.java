package si.rsj.pu.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import si.rsj.pu.entity.Volunteer;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class VolunteerRepository implements PanacheRepositoryBase<Volunteer, UUID> {

    public Optional<Volunteer> findByZtsCode(int ztsCode) {
        return find("ztsCode", ztsCode).firstResultOptional();
    }

    public Optional<Volunteer> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}