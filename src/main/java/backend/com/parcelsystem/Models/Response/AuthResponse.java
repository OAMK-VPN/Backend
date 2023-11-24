package backend.com.parcelsystem.Models.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private boolean active;
    private String token;
    
    @Override
    public String toString() {
        return "AuthResponse [active=" + active + ", token=" + token + "]";
    }

    
}
