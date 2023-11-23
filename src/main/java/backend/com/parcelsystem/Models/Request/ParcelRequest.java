package backend.com.parcelsystem.Models.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParcelRequest {
    private String emailReceiver;

    // check the limit for weith, heigh, width in the client side before the sender sumbit the parcel
    private double weigh;
    private double heigh;
    private double width;
    private double length;
}
