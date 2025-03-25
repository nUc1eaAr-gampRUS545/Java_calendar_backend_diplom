package ru.minusd.security.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "places")
//@RedisHash("Place")
public class Place implements Serializable {
    @Getter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "room_number", nullable = true, length = 100)
    private String roomNumber;

    @Column(name = "zone", nullable = true, length = 30)
    private String zone;

    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @Column(name = "type_place", nullable = true, length = 30)
    private String typePlace;

    @Column(name = "frame", nullable = true, length = 30)
    private String frame;

    @OneToMany(mappedBy = "place",orphanRemoval = true, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Task> tasks;

}
