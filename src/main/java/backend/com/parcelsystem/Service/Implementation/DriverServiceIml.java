package backend.com.parcelsystem.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Repository.DriverRepos;
import backend.com.parcelsystem.Service.DriverService;
import backend.com.parcelsystem.Service.UserService;

@Service
public class DriverServiceIml implements DriverService {
    @Autowired
    DriverRepos driverRepos;
    @Autowired
    UserService userService;

    @Override
    public Driver getByAuthenticatedUser() {
        Users authUser = userService.getAuthUser();
        Optional<Driver> entity = driverRepos.findByUser(authUser);
        if(!entity.isPresent()) {
            Driver driver = new Driver(authUser);
            return driverRepos.save(driver);
        } else {
            Driver driver = entity.get();
            return driver;
        }
    }

    @Override
    public Driver getById(Long id) {
        Optional<Driver> entity = driverRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the courier not found");
        } else {
            Driver courier = entity.get();
            return courier;
        }
    }

    @Override
    public Driver getDriverByemail(String email) {
         Users user = userService.getUserByEmail(email);
        Optional<Driver> entity = driverRepos.findByUser(user);
        if(!entity.isPresent()) {
            Driver driver = new Driver(user);
            return driverRepos.save(driver);
        } else {
            Driver driver = entity.get();
            return driver;
        }
    }

    @Override
    public List<Driver> getDriversByCity(String city) {
        return driverRepos.findDriversByCity(city.toUpperCase());
    }
}
