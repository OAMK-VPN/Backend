package backend.com.parcelsystem.Models;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "Locker")
@Table(name = "locker")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Locker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "name cannot be blank")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Min(value = 0, message = "total cabinets must be higher than 0")
    @Column(name = "total_cabinets", nullable = false)
    private int totalCabinets;

    @NotBlank(message = "city cannot be blank")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "address cannot be blank")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "zipcode cannot be blank")
    @Column(name = "zipcode")
    private String zipcode;

    @JsonIgnore
    @OneToMany(mappedBy = "locker", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Cabinet> cabinets = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "locker", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Code> codes = new ArrayList<>();

    @Override
    public String toString() {
        return "Locker [id=" + id + ", name=" + name + ", totalCabinets=" + totalCabinets + ", city=" + city
                + ", address=" + address + ", zipcode=" + zipcode + "]";
    }

    
}
