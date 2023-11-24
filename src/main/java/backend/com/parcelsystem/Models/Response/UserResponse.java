package backend.com.parcelsystem.Models.Response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;


import backend.com.parcelsystem.Models.Enums.Role;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String fullname;
    private List<Role> roles;
    private String city;
    private String address;
    private String zipcode;
    private boolean active;
    @Override
    public String toString() {
        return "UserResponse [id=" + id + ", username=" + username + ", email=" + email + ", fullname=" + fullname
                + ", roles=" + roles + ", city=" + city + ", address=" + address + ", zipcode=" + zipcode + ", active="
                + active + "]";
    }
    
 
}
