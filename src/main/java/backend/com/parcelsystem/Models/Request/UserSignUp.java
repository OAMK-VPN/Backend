package backend.com.parcelsystem.Models.Request;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUp {
    private String username;
    private String password;
    private String email;
    private String city;
    private String address;
    private String zipcode;
    
    public UserSignUp(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }


   

    
    
    
}
