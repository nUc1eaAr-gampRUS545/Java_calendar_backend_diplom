package ru.minusd.security.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "work_types")
//@RedisHash("Work_type")
public class WorkType {
    @Getter
    @jakarta.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "description", nullable = false, length = 255)
    private String description;

    @OneToMany(mappedBy = "workType",fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Application> applications = new HashSet<>();
}
