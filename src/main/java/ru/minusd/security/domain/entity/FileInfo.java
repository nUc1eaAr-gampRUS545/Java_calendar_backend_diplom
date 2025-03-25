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
@Getter
@ToString
@Table(name = "files_info")
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo implements Serializable {

    @jakarta.persistence.Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name", nullable = false)
    private String name;
    @Column(name = "file_size", nullable = false)
    private Long size;
    @Column(name = "file_key", nullable = false)
    private String key;
    @Column(name = "upload_date", nullable = false)
    private LocalDate uploadDate;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "files", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    Set<Task> tasks = new HashSet<>();
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "files", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    Set<Application> applications = new HashSet<>();

}
