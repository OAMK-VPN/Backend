package backend.com.parcelsystem.Utils;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Models.Enums.Role;
import backend.com.parcelsystem.Repository.UserRepos;

@Component
public class SampleGenerator {
    @Autowired
    UserRepos userRepos;

    public Users generateUser(String username, String email, String fullname, String city, String address, String zipcode) {
        Users user = new Users(username, new BCryptPasswordEncoder().encode("123456"), fullname, email, city, address, zipcode);
        user.getRoles().add(Role.USER);
        user.setActive(true);
        
        Users result = userRepos.findByEmail(user.getEmail()).orElseGet(() -> userRepos.save(user));
        return result;
    }
}
