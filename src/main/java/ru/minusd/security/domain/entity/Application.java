package ru.minusd.security.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
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
public class Application implements Serializable {
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

    @Column(name = "phonenumber", nullable = false, length = 16)
    private String phoneNumber;

    @Column(name = "created_at", nullable = false, length = 25)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false, length = 25)
    private LocalDate updatedAt;

    @Column(name = "task_start_date", nullable = false, length = 25)
    private LocalDate startDate;
    @Column(name = "task_due_date", nullable = false, length = 25)
    private LocalDate dueDate;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinTable(
            name = "files_applications",
            joinColumns = @JoinColumn(name = "file_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "application_id", referencedColumnName = "id")
    )
    private Set<FileInfo> files = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name = "organization_id")
    private Organization organizationByApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id")

    private User createdByUserApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "responsible_user_id")

    private User responsiblePersonApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "zone_owner_id")
    private User zoneOwnerApplication;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_work_id")
    private WorkType workType;



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


