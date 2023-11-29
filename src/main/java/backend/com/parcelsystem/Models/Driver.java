package backend.com.parcelsystem.Models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
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

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", referencedColumnName = "id", unique = true)
    private Users user;

    @JsonIgnore
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Parcel> parcels = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "driver", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Notification> notifications = new ArrayList<>();

    public Driver(Users user) {
        this.user = user;
    }

    
}
