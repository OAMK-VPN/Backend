package backend.com.parcelsystem.Models.Enums;

public enum ParcelStatus {
    WAITING_FOR_SENDER("WAITING_FOR_SENDER"),
    WAITING_FOR_RECEIVER("WAITING_FOR_RECEIVER"),
    WAITING_FOR_DRIVER("WAITING_FOR_DRIVER"),
    ASSIGNING_NEW_CABINET_FOR_PICKUP("ASSIGNING_NEW_CABINET_FOR_PICKUP"),
    IN_DELIVERY("IN_DELIVERY"),
    PICKED_UP_BY_RECIEVER("PICKED_UP_BY_RECIEVER"),
    RETURN_STORAGE_DUE_TO_EXPIRY("RETURN_STORAGE_DUE_TO_EXPIRY"),
    PARCEL_CANCELLED_DUE_TO_SENDING_LATE_BY_SENDER("PARCEL_CANCELLED_DUE_TO_SENDING_LATE_BY_SENDER"),
    GENERATE_BY_ROBOT("GENERATE_BY_ROBOT"),
    FAILED("FAILED");
    
    private String name;

    ParcelStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
