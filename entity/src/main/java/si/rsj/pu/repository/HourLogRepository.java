package si.rsj.pu.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import si.rsj.pu.entity.HourLog;

import java.util.UUID;

@ApplicationScoped
public class HourLogRepository implements PanacheRepositoryBase<HourLog, UUID> {

}
