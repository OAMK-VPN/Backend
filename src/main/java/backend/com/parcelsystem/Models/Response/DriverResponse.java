package backend.com.parcelsystem.Models.Response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponse {
    private Long id;
    private UserResponse user;
}
