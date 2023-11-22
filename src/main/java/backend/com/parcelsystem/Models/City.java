package backend.com.parcelsystem.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.*;



@Entity(name = "City")
@Table(name = "city")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class City {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;
    

    @NotBlank(message = "name cannot be blank")
    @Column(name = "name", nullable = false, unique = true)
    private String name;


    public City( String name) {
        this.name = name;
    }

    
}
