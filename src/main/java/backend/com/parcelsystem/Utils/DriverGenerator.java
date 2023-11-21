package backend.com.parcelsystem.Utils;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Enums.Role;
import backend.com.parcelsystem.Repository.DriverRepos;

@Component
public class DriverGenerator {
    @Autowired
    DriverRepos driverRepos;

    public Driver generateUser(String username, String email) {
        Driver driver = new Driver(username, new BCryptPasswordEncoder().encode("123456"),email);
        driver.getRoles().add(Role.DRIVER);
        driver.setActive(true);
        
        driverRepos.save(driver);
        return driver;
    }
}
