package backend.com.parcelsystem.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Mapper.ParcelMapper;
import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import backend.com.parcelsystem.Models.Request.ParcelRequest;
import backend.com.parcelsystem.Service.ParcelService;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {
    @Autowired
    ParcelService parcelService;

    @Autowired
    ParcelMapper parcelMapper;

    
    // authenticated
    @GetMapping("/receiver")
    public List<Parcel> getAllByAuthReceiver() {
        return parcelService.getAllByAuthReceiver();
    }

     // authenticated
    @GetMapping("/sender")
    public List<Parcel> getAllByAuthSender() {
        return parcelService.getAllByAuthSender();
    }

     // authenticated
    @GetMapping("/driver")
    public List<Parcel> getALLByAuthDriver() {
        return parcelService.getALLByAuthDriverAndStatus();
    }

    // authenticated
    @GetMapping("/parcel/{id}")
    public Parcel getParcelById(@PathVariable Long id) {
        return parcelService.getParcelById(id);
    }

    @GetMapping("/unsecure/tracking/{trackingNumber}")
    public Parcel trackingParcel(@PathVariable String trackingNumber) {
        return parcelService.trackingParcel(trackingNumber);
    }

    @PostMapping("/buy")
    public Parcel buyParcel(@RequestBody ParcelRequest req) {
        return parcelService.buyParcel(req);
    }

    @PutMapping("/unsecure/drop-off/locker/{lockerId}/code/{code}")
    public Parcel dropOffParcelIntoCabinet(@PathVariable Long lockerId, @PathVariable String code) {
        return parcelService.dropOffParcelIntoCabinet(lockerId, code);
    }

    @PutMapping("/unsecure/picked-up/locker/{lockerId}/code/{code}")
    public Parcel pickedUpParcelByReceiver(@PathVariable Long lockerId, @PathVariable String code) {
        return parcelService.pickedUpParcelByReceiver(lockerId, code);
    }

    @PutMapping("/assign-all-to-drivers")
    public List<Parcel> assignAllParcelsToDrivers() {
        return parcelService.assignAllParcelsToDrivers();
    }

    @PutMapping("/check-pickup-expired")
    public List<Parcel> checkAllPickupExpiredParcels() {
        return parcelService.CheckAllPickupExpiredParcels();
    }

    @PutMapping("/check-send-expired")
    public List<Parcel> checkAllSendExpiredParcels() {
        return parcelService.CheckAllSendExpiredParcels();
    }
}
