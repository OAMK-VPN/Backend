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
    private String fullname;
    private String email;
    private String city;
    private String address;
    private String zipcode;
    private String driverCode;
    
    public UserSignUp(String username, String password, String fullname, String email, String city, String address,
            String zipcode) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.city = city;
        this.address = address;
        this.zipcode = zipcode;
    }

    @Override
    public String toString() {
        return "UserSignUp [username=" + username + ", password=" + password + ", fullname=" + fullname + ", email="
                + email + ", city=" + city + ", address=" + address + ", zipcode=" + zipcode + ", driverCode="
                + driverCode + "]";
    }
    
    


   

    
    
    
}
