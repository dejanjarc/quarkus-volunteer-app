package si.rsj.pu.entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "event")
public class Event {

    @Id
    public UUID id;

    @Column(name = "name", nullable = false)
    public String name;

    @Column(name = "description")
    public String description;

    @Column(name = "location")
    public String location;

    @Column(name = "start_date", nullable = false)
    public OffsetDateTime startDate;

    @Column(name = "end_date", nullable = false)
    public OffsetDateTime endDate;

    @Column(name = "created_at", nullable = false)
    public OffsetDateTime createdAt;

    @OneToMany(mappedBy = "event", fetch = FetchType.LAZY)
    public List<HourLog> hourLogs = new ArrayList<>();
}