package ru.minusd.security.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "places")
//@RedisHash("Place")
public class Place {
    @Getter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "room_number", nullable = true, length = 100)
    private String roomNumber;

    @Column(name = "zone", nullable = true, length = 15)
    private String zone;

    @Column(name = "quantity", nullable = true)
    private Integer quantity;

    @Column(name = "type_place", nullable = true, length = 30)
    private String typePlace;

    @Column(name = "frame", nullable = true, length = 30)
    private String frame;

    @OneToMany(mappedBy = "place")
    @JsonIgnore
    private List<Task> tasks;

}
