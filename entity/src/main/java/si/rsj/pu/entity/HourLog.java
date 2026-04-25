package si.rsj.pu.entity;

import jakarta.persistence.*;
import si.rsj.pu.entity.enums.EventRole;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "hour_log")
public class HourLog {
    @Id
    public UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "volunteer_id", nullable = false)
    public Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "event_id", nullable = false)
    public Event event;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_role", nullable = false)
    public EventRole eventRole;

    @Column(name = "work_date", nullable = false)
    public OffsetDateTime workDate;

    @Column(name = "hours_worked", nullable = false)
    public int hoursWorked;

    @Column(name = "description")
    public String description;

    @Column(name = "submitted_at", nullable = false)
    public OffsetDateTime submittedAt;


}
