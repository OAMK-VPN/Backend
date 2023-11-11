package backend.com.parcelsystem.Models;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity(name = "Parcel")
@Table(name = "parcel")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Parcel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParcelStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    private Receiver receiver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    private Sender sender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cabinet_id", referencedColumnName = "id")
    private Cabinet cabinet;

    @Min(value = 0, message = "weight must be higher than 0")
    @Column(name = "weight", nullable = false)
    private double weigh;

    @Min(value = 0, message = "heigh must be higher than 0")
    @Column(name = "heigh", nullable = false)
    private double heigh;

    @Min(value = 0, message = "width must be higher than 0")
    @Column(name = "width", nullable = false)
    private double width;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "send_date", nullable = false)
    private LocalDateTime sendDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "pickup_availability")
    private LocalDateTime pickupAvailability;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "pickup_expiry")
    private LocalDateTime pickupExpiry;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "date_created", nullable = false)
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated;
}
