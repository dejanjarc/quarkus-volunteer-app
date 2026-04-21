package si.rsj.pu.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import si.rsj.pu.entity.VolunteerEntity;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class VolunteerRepository implements PanacheRepositoryBase<VolunteerEntity, UUID> {

    public Optional<VolunteerEntity> findByZtsCode(int ztsCode) {
        return find("ztsCode", ztsCode).firstResultOptional();
    }

    public Optional<VolunteerEntity> findByEmail(String email) {
        return find("email", email).firstResultOptional();
    }
}