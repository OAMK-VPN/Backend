package backend.com.parcelsystem.Models.Response;

import java.time.LocalDateTime;

import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParceResponse {
     private Long id;
    private String trackingNumber;
    private ParcelStatus status;
    private String city;
    private String address;
    private String zipcode;
    private Long receiverId;
    private Long senderId;
    private Long driverId;
    private Long cabinetId;
    private double weigh;
    private double heigh;
    private double width;
    private LocalDateTime sendDateSender;
    private LocalDateTime pickupDate;
    private LocalDateTime sendDateDriver;
    private LocalDateTime receiveDateDriver;
    private boolean pickupAvailability;
    private LocalDateTime pickupExpiry;
    private LocalDateTime sendExpiry;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;
}
