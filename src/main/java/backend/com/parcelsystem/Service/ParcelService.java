package backend.com.parcelsystem.Service;

import java.util.Date;
import java.util.List;

import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;

public interface ParcelService {
    List<Parcel> getAllByReceiver(Long receiverId);
    List<Parcel> getAllBySender(Long senderId);
    List<Parcel> getALLByDriverAndStatusAndDate(Long driver, ParcelStatus status, Date date);
    Parcel buyParcel();
    Parcel placeParcelBySender();
    Parcel sendParcelToDriver();
    Parcel deliveredParcelToLockerByDriver();
    Parcel pickedUpParcelByReceiver();
}
