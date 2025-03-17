package ru.minusd.security.domain.model;

import jakarta.persistence.*;
import lombok.*;

import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "applications")
//@RedisHash("Application")
public class Application {
    @jakarta.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firstname", nullable = false, length = 25)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 25)
    private String lastname;

    @Column(name = "email", nullable = false, length = 35)
    private String email;

    @Column(name = "phonenumber", nullable = false, length = 11)
    private String phoneNumber;

    @Column(name = "created_at", nullable = false, length = 25)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false, length = 25)
    private LocalDate updatedAt;

    @Column(name = "task_start_date", nullable = false, length = 25)
    private LocalDate startDate;

    @Column(name = "task_due_date", nullable = false, length = 25)
    private LocalDate dueDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "files_applications",
            joinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "application_id", referencedColumnName = "id")
    )
    private Set<FileInfo> files =  new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")

    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "created_user_id")

    private User createdByUser;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "responsible_user_id")

    private User responsiblePerson;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "zone_owner_id")
    private User zoneOwner;

    @Column(name = "zoneOwner_approval", nullable = false)
    private boolean zoneOwnerApproval = false;

    @Column(name = "security_approval", nullable = false)
    private boolean securityApproval = false;

    @Column(name = "is_complited", nullable = false)
    private boolean isCompleted = false;

    @Column(name = "is_safety_briefing_completed", nullable = false)
    private Boolean isSafetyBriefingCompleted;

    @Column(name = "is_electrical_safety_training_completed", nullable = false)
    private Boolean isElectricalSafetyTrainingCompleted;

    @Column(name = "is_fire_safety_training_completed", nullable = false)
    private Boolean isFireSafetyTrainingCompleted;

}


