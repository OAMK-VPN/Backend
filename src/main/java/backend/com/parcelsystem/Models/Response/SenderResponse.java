package backend.com.parcelsystem.Models.Response;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SenderResponse {
    private Long id;
    private UserResponse user;
}
