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
public class DriverSignUp {
    private String username;
    private String password;
    private String email;

    
    /**
     * @param username
     * @param password
     */
    public DriverSignUp(String username, String password) {
        this.username = username;
        this.password = password;
        
    }


   

    
    
    
}

