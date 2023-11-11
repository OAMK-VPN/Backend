package backend.com.parcelsystem.Models.Enums;

public enum ParcelStatus {
    WAITING_FOR_SENDER("WAITING_FOR_SENDER"),
    IN_DELIVERY("IN_DELIVERY"),
    WAITING_FOR_RECEIVER("WAITING_FOR_RECEIVER"),
    PICKED_UP_BY_RECIEVER("PICKED_UP_BY_RECIEVER"),
    FAILED("FAILED");
    
    private String name;

    ParcelStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
