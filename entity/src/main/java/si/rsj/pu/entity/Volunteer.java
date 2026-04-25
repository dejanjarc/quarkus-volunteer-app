package si.rsj.pu.entity;

import jakarta.persistence.*;
import si.rsj.pu.entity.enums.VolunteerRole;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "volunteer")
public class Volunteer {

    @Id
    public UUID id;

    @Column(name = "zts_code", nullable = false, unique = true)
    public int ztsCode;

    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "volunteer_role", nullable = false)
    public VolunteerRole volunteerRole;

    @Column(name = "phone_number")
    public String phoneNumber;

    @Column(name = "email", unique = true)
    public String email;

    @Column(name = "active", nullable = false)
    public boolean active;

    @Column(name = "joined_at", nullable = false)
    public OffsetDateTime joinedAt;

    @OneToMany(mappedBy = "volunteer", fetch = FetchType.LAZY)
    public List<HourLog> hourLogs = new ArrayList<>();
}