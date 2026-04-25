package si.rsj.pu.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import si.rsj.pu.entity.Event;

import java.util.UUID;

@ApplicationScoped
public class EventRepository implements PanacheRepositoryBase<Event, UUID> {
}