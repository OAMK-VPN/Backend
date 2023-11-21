package backend.com.parcelsystem.Models.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DriverSignIn {
    private String username;
    private String password;
    private String email;


    public DriverSignIn(String username, String password) {
        this.username = username;
        this.password = password;
    }

    
}