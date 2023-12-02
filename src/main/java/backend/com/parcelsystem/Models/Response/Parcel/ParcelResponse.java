package backend.com.parcelsystem.Models.Response.Parcel;

import java.time.LocalDate;
import java.time.LocalDateTime;

import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ParcelResponse {
    private Long id;
    private ParcelStatus status;
    private String trackingNumber;
    private String city;
    private String address;
    private String zipcode;
    private People receiver;
    private People sender;
    private People driver;
    private CabinetResponse cabinet;
    private double weigh;
    private double height;
    private double width;
    private double length;
    private LocalDateTime sendDateSender;
    private LocalDateTime pickupDate;
    private boolean pickupAvailability;
    private LocalDateTime sendDateDriver;
    private LocalDateTime receiveDateDriver;
    private LocalDateTime pickupExpiry;
    private LocalDateTime sendExpiry;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;


    public ParcelResponse(Long id, ParcelStatus status, String trackingNumber, String city, String address, String zipcode, People receiver,
            People sender, CabinetResponse cabinet, double weigh, double height, double width, double length,
            LocalDateTime sendDateSender, LocalDateTime pickupDate, boolean pickupAvailability,
            LocalDateTime receiveDateDriver, LocalDateTime pickupExpiry, LocalDateTime sendExpiry,
            LocalDateTime dateCreated, LocalDateTime dateUpdated, LocalDateTime sendDateDriver) {
        this.id = id;
        this.status = status;
        this.trackingNumber = trackingNumber;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
        this.receiver = receiver;
        this.sender = sender;
        this.cabinet = cabinet;
        this.weigh = weigh;
        this.height = height;
        this.width = width;
        this.length = length;
        this.sendDateSender = sendDateSender;
        this.pickupDate = pickupDate;
        this.pickupAvailability = pickupAvailability;
        this.receiveDateDriver = receiveDateDriver;
        this.pickupExpiry = pickupExpiry;
        this.sendExpiry = sendExpiry;
        this.dateCreated = dateCreated;
        this.dateUpdated = dateUpdated;
        this.sendDateDriver = sendDateDriver;
    }
    
    
    
    
}
