package backend.com.parcelsystem.Models.Enums;

public enum Role {
    USER("USER"),
    DRIVER("DRIVER"),
    RECEIVER("RECEIVER"),
    SENDER("SENDER");

    private String name;

    Role(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
