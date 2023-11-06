package backend.com.parcelsystem.Models.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignIn {
    private String username;
    private String password;
    private Double longitude;
    private Double latitude;

    public UserSignIn(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
}
