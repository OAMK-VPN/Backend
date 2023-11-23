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

    @NotBlank(message = "name cannot be blank")
    @Column(name = "tracking_number", nullable = false, unique = true)
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ParcelStatus status;

    @NotBlank(message = "city cannot be blank")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "address cannot be blank")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "zipcode cannot be blank")
    @Column(name = "zipcode")
    private String zipcode;


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

    @Min(value = 0, message = "length must be higher than 0")
    @Column(name = "length", nullable = false)
    private double length;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "send_date")
    private LocalDateTime sendDateSender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "pickup_date")
    private LocalDateTime pickupDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "send_date_driver")
    private LocalDateTime sendDateDriver;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "receive_date_driver")
    private LocalDateTime receiveDateDriver;

    @Column(name = "pickup_availability")
    private boolean pickupAvailability;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "pickup_expiry")
    private LocalDateTime pickupExpiry;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "send_expiry")
    private LocalDateTime sendExpiry;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated;

    public Parcel( String trackingNumber, ParcelStatus status, String city, String address, String zipcode, Receiver receiver, Sender sender, double weigh, double heigh, double width, double length) {
        this.trackingNumber = trackingNumber;
        this.status = status;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.receiver = receiver;
        this.sender = sender;
        this.weigh = weigh;
        this.heigh = heigh;
        this.width = width;
        this.length = length;
        this.pickupAvailability = false;
    }

    @Override
    public String toString() {
        return "Parcel [id=" + id + ", trackingNumber=" + trackingNumber + ", status=" + status + ", city=" + city
                + ", address=" + address + ", zipcode=" + zipcode + ", receiver=" + receiver + ", sender=" + sender
                + ", driver=" + driver + ", cabinet=" + cabinet + ", weigh=" + weigh + ", heigh=" + heigh + ", width="
                + width + ", sendDateSender=" + sendDateSender + ", pickupDate=" + pickupDate + ", sendDateDriver="
                + sendDateDriver + ", receiveDateDriver=" + receiveDateDriver + ", pickupAvailability="
                + pickupAvailability + ", pickupExpiry=" + pickupExpiry + ", sendExpiry=" + sendExpiry
                + ", dateCreated=" + dateCreated + ", dateUpdated=" + dateUpdated + "]";
    }


    
}
