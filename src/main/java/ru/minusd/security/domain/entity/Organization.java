package ru.minusd.security.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Table(name = "organizations")
public class Organization implements Serializable {
    @Getter
    @Column(name = "id")
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @Column(name = "created_at", nullable = false, length = 25)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false, length = 25)
    private LocalDate updatedAt;

    @OneToMany(mappedBy = "organizationByUser", fetch = FetchType.EAGER,orphanRemoval = true)
    @JsonIgnore
    private Set<User> users;

    @OneToMany(mappedBy = "organizationByApplication", orphanRemoval = true,cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH})
    @JsonIgnore
    private Set<Application> organizationApplications = new HashSet<>();
}