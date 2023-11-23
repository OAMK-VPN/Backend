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
    private String email;
    private String password;
    private boolean isDriver;

    public UserSignIn(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserSignIn [email=" + email + ", password=" + password + ", isDriver=" + isDriver + "]";
    }

    
}
