package backend.com.parcelsystem.Models.Response;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostnordLocation {
    private String name;
    private String city;
    private String address;
    private String zipcode;
    private int totalCabinets;
}
