package backend.com.parcelsystem.Models.Response;

import java.util.List;

import backend.com.parcelsystem.Models.Enums.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverResponse {
   private  Long id;
    private String username;
    private String email;
    private List<Role> roles;
   
}
