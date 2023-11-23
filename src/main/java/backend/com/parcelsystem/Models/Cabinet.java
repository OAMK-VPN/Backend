package backend.com.parcelsystem.Models;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "Cabinet")
@Table(name = "cabinet")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cabinet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Min(value = 0, message = "num must be higher than 0")
    @Column(name = "num", nullable = false)
    private int num;

    @Min(value = 0, message = "weight must be higher than 0")
    @Column(name = "weight", nullable = false)
    private double weigh;

    @Min(value = 0, message = "heigh must be higher than 0")
    @Column(name = "heigh", nullable = false)
    private double heigh;

    @Min(value = 0, message = "width must be higher than 0")
    @Column(name = "width", nullable = false)
    private double width;

    @Min(value = 0, message = "length must be higher than 0")
    @Column(name = "length", nullable = false)
    private double length;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "locker_id", referencedColumnName = "id")
    private Locker locker;

    
    @OneToOne(mappedBy = "cabinet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Code code;

    @Column(name = "empty", nullable = false)
    private boolean empty;

    @Column(name = "filled", nullable = false)
    private boolean filled;

    @JsonIgnore
    @OneToMany(mappedBy = "cabinet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Parcel> parcels = new ArrayList<>();

    @Override
    public String toString() {
        return "Cabinet [id=" + id + ", num=" + num + ", weigh=" + weigh + ", heigh=" + heigh + ", width=" + width
                + ", locker=" + locker + ", code=" + code + ", empty=" + empty + ", filled=" + filled + "]";
    }

    
}
