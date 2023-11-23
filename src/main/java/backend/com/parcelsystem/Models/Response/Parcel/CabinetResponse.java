package backend.com.parcelsystem.Models.Response.Parcel;
import backend.com.parcelsystem.Models.Locker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CabinetResponse {
    private int number;
    private Locker locker;
    private String code;
    private boolean empty;
    private boolean filled;
}
