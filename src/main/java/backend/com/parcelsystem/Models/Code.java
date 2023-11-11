package backend.com.parcelsystem.Models;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "Code")
@Table(name = "code")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Code {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "code cannot be blank")
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name = "cabinet_id", referencedColumnName = "id")
    private Cabinet cabinet;
}
