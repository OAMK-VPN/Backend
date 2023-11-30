package backend.com.parcelsystem.Service;

import java.util.List;

import backend.com.parcelsystem.Models.Driver;

public interface DriverService {
    Driver getById(Long id);
    Driver getByAuthenticatedUser();
    Driver getDriverByemail(String email);
    List<Driver> getDriversByCity(String city);
}
