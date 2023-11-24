package backend.com.parcelsystem.Models;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.com.parcelsystem.Models.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "username cannot be blank")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @JsonIgnore
    @NotBlank(message = "password cannot be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "email cannot be blank")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "fullname cannot be blank")
    @Column(name = "fullname", nullable = false)
    private String fullname;


    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @NotBlank(message = "city cannot be blank")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "address cannot be blank")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "zipcode cannot be blank")
    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "active")
    private boolean active;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Driver driver;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Receiver receiver;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Sender sender;

    public Users(String username, String password, String fullname, String email, String city, String address, String zipcode) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.active = true;
    }

    @Override
    public String toString() {
        return "Users [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
                + ", fullname=" + fullname + ", roles=" + roles + ", city=" + city + ", address=" + address
                + ", zipcode=" + zipcode + ", active=" + active + ", driver=" + driver + ", receiver=" + receiver
                + ", sender=" + sender + "]";
    }

    
}
