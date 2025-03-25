package ru.minusd.security.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

@Entity
@Builder(toBuilder = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
//@RedisHash("User")
public class User implements UserDetails, Serializable {
    @Getter
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
//    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "username", nullable = false, length = 15)
    private String username;

    @Column(name = "firstname", nullable = false, length = 25)
    private String firstname;

    @Column(name = "lastname", nullable = false, length = 25)
    private String lastname;

    @Column(name = "email", nullable = false, length = 35)
    private String email;

    @Column(name = "phonenumber", nullable = false, length = 11)
    private String phoneNumber;

    @Column(name = "password", length = Integer.MAX_VALUE)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "users", cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    private Set<Task> tasks =  new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "organization_id")
    private Organization organizationByUser;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }
    @OneToMany(mappedBy = "createdUserTask",cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Task> taskByCreatedUser = new ArrayList<>();

    @OneToMany(mappedBy = "createdByUserApplication",  fetch = FetchType.LAZY,orphanRemoval = true)
    @JsonIgnore
    private Set<Application> createdApplications = new HashSet<>();

    @OneToMany(mappedBy = "responsiblePersonApplication",fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Application> responsibleApplications = new HashSet<>();

    @OneToMany(mappedBy = "zoneOwnerApplication",fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonIgnore
    private Set<Application> ownedZoneApplications = new HashSet<>();

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

