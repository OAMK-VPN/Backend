package backend.com.parcelsystem.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import backend.com.parcelsystem.Models.Response.ParceResponse;
import backend.com.parcelsystem.Models.Response.Parcel.CabinetResponse;
import backend.com.parcelsystem.Models.Response.Parcel.ParcelResponse;
import backend.com.parcelsystem.Models.Response.Parcel.People;

@Component
public class ParcelMapper {
    @Autowired
    private ModelMapper modelMapper;

    public ParcelResponse mapParcelToResponseForReceiver(Parcel parcel) {
        People receiver = new People(parcel.getReceiver().getUser().getFullname(), parcel.getReceiver().getUser().getCity());
        People sender = null;
        if(parcel.getSender() != null) {
            sender = new People(parcel.getSender().getUser().getFullname(), parcel.getSender().getUser().getCity());
        }
        

        Cabinet cabinet;
        CabinetResponse cabinetRes = null;
        if(parcel.getCabinet() != null) {
            cabinet = parcel.getCabinet();
            cabinetRes = new CabinetResponse(cabinet.getNum(), cabinet.getLocker(), "", cabinet.isEmpty(), cabinet.isFilled());
            if (parcel.getStatus().equals(ParcelStatus.WAITING_FOR_RECEIVER)) {
                cabinetRes.setCode(cabinet.getCode().getCode());
            }
        }

        return new ParcelResponse(
                parcel.getId(),
                parcel.getStatus(),
                parcel.getTrackingNumber(),
                parcel.getCity(),
                parcel.getAddress(),
                parcel.getZipcode(),
                receiver,
                sender,
                cabinetRes,
                parcel.getWeigh(),
                parcel.getHeigh(),
                parcel.getWidth(),
                parcel.getLength(),
                parcel.getSendDateSender(),
                parcel.getPickupDate(),
                parcel.isPickupAvailability(), 
                parcel.getReceiveDateDriver(),
                parcel.getPickupExpiry(),
                parcel.getSendExpiry(),
                parcel.getDateCreated(),
                parcel.getDateUpdated(),
                parcel.getSendDateDriver()
        );
    }

    public ParcelResponse mapParcelToResponseForSender(Parcel parcel) {
        People receiver = new People(parcel.getReceiver().getUser().getFullname(), parcel.getReceiver().getUser().getCity());
        People sender = null;
        if(parcel.getSender() != null) {
            sender = new People(parcel.getSender().getUser().getFullname(), parcel.getSender().getUser().getCity());
        }
        Cabinet cabinet;
        CabinetResponse cabinetRes = null;
        if(parcel.getCabinet() != null) {
            cabinet = parcel.getCabinet();
            cabinetRes = new CabinetResponse(cabinet.getNum(), cabinet.getLocker(), "", cabinet.isEmpty(), cabinet.isFilled());
            if (parcel.getStatus().equals(ParcelStatus.WAITING_FOR_SENDER)) {
                cabinetRes.setCode(cabinet.getCode().getCode());
            }
        }

        return new ParcelResponse(
                parcel.getId(),
                parcel.getStatus(),
                parcel.getTrackingNumber(),
                parcel.getCity(),
                parcel.getAddress(),
                parcel.getZipcode(),
                receiver,
                sender,
                cabinetRes,
                parcel.getWeigh(),
                parcel.getHeigh(),
                parcel.getWidth(),
                parcel.getLength(),
                parcel.getSendDateSender(),
                parcel.getPickupDate(),
                parcel.isPickupAvailability(), 
                parcel.getReceiveDateDriver(),
                parcel.getPickupExpiry(),
                parcel.getSendExpiry(),
                parcel.getDateCreated(),
                parcel.getDateUpdated(),
                parcel.getSendDateDriver()
        );
    }

     public ParcelResponse mapParcelToResponseForDriverApp(Parcel parcel) {
        People receiver = new People(parcel.getReceiver().getUser().getFullname(), parcel.getReceiver().getUser().getCity());
        People sender = null;
        if(parcel.getSender() != null) {
            sender = new People(parcel.getSender().getUser().getFullname(), parcel.getSender().getUser().getCity());
        }
        People driver = new People(parcel.getDriver().getUser().getFullname(), parcel.getDriver().getUser().getCity());

        Cabinet cabinet;
        CabinetResponse cabinetRes = null;
        if(parcel.getCabinet() != null) {
            cabinet = parcel.getCabinet();
            cabinetRes = new CabinetResponse(cabinet.getNum(), cabinet.getLocker(), "", cabinet.isEmpty(), cabinet.isFilled());
            
            if (parcel.getStatus().equals(ParcelStatus.IN_DELIVERY)) {
                cabinetRes.setCode(cabinet.getCode().getCode());
            }
        }

        return new ParcelResponse(
                parcel.getId(),
                parcel.getStatus(),
                parcel.getTrackingNumber(),
                parcel.getCity(),
                parcel.getAddress(),
                parcel.getZipcode(),
                receiver,
                sender,
                driver,
                cabinetRes,
                parcel.getWeigh(),
                parcel.getHeigh(),
                parcel.getWidth(),
                parcel.getLength(),
                parcel.getSendDateSender(),
                parcel.getPickupDate(),
                parcel.isPickupAvailability(), 
                parcel.getReceiveDateDriver(),
                parcel.getPickupExpiry(),
                parcel.getSendExpiry(),
                parcel.getDateCreated(),
                parcel.getDateUpdated(),
                parcel.getSendDateDriver()
        );
    }

    public ParcelResponse mapParcelToResponseForParcelID(Parcel parcel, String role) {
        People receiver = new People(parcel.getReceiver().getUser().getFullname(), parcel.getReceiver().getUser().getCity());
        People sender = null;
        if(parcel.getSender() != null) {
            sender = new People(parcel.getSender().getUser().getFullname(), parcel.getSender().getUser().getCity());
        }
        Cabinet cabinet;
        CabinetResponse cabinetRes = null;
        if(parcel.getCabinet() != null) {
            cabinet = parcel.getCabinet();
            cabinetRes = new CabinetResponse(cabinet.getNum(), cabinet.getLocker(), "", cabinet.isEmpty(), cabinet.isFilled());

            if (parcel.getStatus().equals(ParcelStatus.IN_DELIVERY) && role.equals("DRIVER")) {
                cabinetRes.setCode(cabinet.getCode().getCode());
            } else if (parcel.getStatus().equals(ParcelStatus.WAITING_FOR_RECEIVER) && role.equals("RECEIVER")) {
                cabinetRes.setCode(cabinet.getCode().getCode());
            } if (parcel.getStatus().equals(ParcelStatus.WAITING_FOR_SENDER) && role.equals("SENDER")) {
                cabinetRes.setCode(cabinet.getCode().getCode());
            }
        }

        return new ParcelResponse(
                parcel.getId(),
                parcel.getStatus(),
                parcel.getTrackingNumber(),
                parcel.getCity(),
                parcel.getAddress(),
                parcel.getZipcode(),
                receiver,
                sender,
                cabinetRes,
                parcel.getWeigh(),
                parcel.getHeigh(),
                parcel.getWidth(),
                parcel.getLength(),
                parcel.getSendDateSender(),
                parcel.getPickupDate(),
                parcel.isPickupAvailability(), 
                parcel.getReceiveDateDriver(),
                parcel.getPickupExpiry(),
                parcel.getSendExpiry(),
                parcel.getDateCreated(),
                parcel.getDateUpdated(),
                parcel.getSendDateDriver()
        );
    }

    public ParcelResponse mapParcelToResponseForTrackingNumber(Parcel parcel) {
        People receiver = new People(parcel.getReceiver().getUser().getFullname(), parcel.getReceiver().getUser().getCity());
         People sender = null;
        if(parcel.getSender() != null) {
            sender = new People(parcel.getSender().getUser().getFullname(), parcel.getSender().getUser().getCity());
        }
        Cabinet cabinet;
        CabinetResponse cabinetRes = null;
        if(parcel.getCabinet() != null) {
            cabinet = parcel.getCabinet();
            cabinetRes = new CabinetResponse(cabinet.getNum(), cabinet.getLocker(), "", cabinet.isEmpty(), cabinet.isFilled());
        }


        return new ParcelResponse(
                parcel.getId(),
                parcel.getStatus(),
                parcel.getTrackingNumber(),
                parcel.getCity(),
                parcel.getAddress(),
                parcel.getZipcode(),
                receiver,
                sender,
                cabinetRes,
                parcel.getWeigh(),
                parcel.getHeigh(),
                parcel.getWidth(),
                parcel.getLength(),
                parcel.getSendDateSender(),
                parcel.getPickupDate(),
                parcel.isPickupAvailability(), 
                parcel.getReceiveDateDriver(),
                parcel.getPickupExpiry(),
                parcel.getSendExpiry(),
                parcel.getDateCreated(),
                parcel.getDateUpdated(),
                parcel.getSendDateDriver()
        );
    }
}
