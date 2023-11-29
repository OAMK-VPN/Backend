package backend.com.parcelsystem.Service;

import java.util.Date;
import java.util.List;

import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import backend.com.parcelsystem.Models.Request.ParcelRequest;

public interface ParcelService {
    List<Parcel> getAllByAuthReceiver();

    List<Parcel> getAllByAuthSender();

    List<Parcel> getALLByAuthDriverAndStatus();

    List<Parcel> getByParcelStatus(ParcelStatus status);

    Parcel trackingParcel(String trackingNumber);

    Parcel getParcelById(Long id);

    Parcel buyParcel(ParcelRequest req);

    Parcel dropOffParcelIntoCabinet(Long lockerId, String code);

    Parcel pickedUpParcelByReceiver(Long lockerId, String code);

    Parcel assignParcelToNewCabinet(Parcel parcel);

    Parcel assignParcelToDriver(Parcel parce, Driver driver);

    List<Parcel> getAllByCityAndStatus(String city, ParcelStatus status);

    List<Parcel> assignAllParcelsToNewCabinets();

    // do cronjob
    List<Parcel> assignAllParcelsToDrivers();

    // do cronjob
    List<Parcel> CheckAllPickupExpiredParcels();

    // do cronjob
    List<Parcel> CheckAllSendExpiredParcels();

    // do cronjob
    // do later after testing all other controllers
    List<Parcel> generateParcelsAndSendToDriversByRobot();

}
