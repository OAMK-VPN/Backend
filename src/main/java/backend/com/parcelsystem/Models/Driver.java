package backend.com.parcelsystem.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.com.parcelsystem.Models.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "Driver")
@Table(name = "driver")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "driver_roles", joinColumns = @JoinColumn(name = "driver_id", nullable = true))
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @NotBlank(message = "username cannot be blank")
    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @NotBlank(message = "password cannot be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "email cannot be blank")
    @Column(name = "email", nullable = true, unique = true)
    private String email;

    @Column(name = "active")
    private boolean active;
    // We have only one driver. All different users' parcels are delivered by this driver. So, we don't need the below code snippet.
    /* @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Users user; */

    @JsonIgnore
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Parcel> parcels = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    public Driver(String username, String password, String email) {
        this.userName = username;
        this.password = password;
        this.email = email;
    }

    
}
