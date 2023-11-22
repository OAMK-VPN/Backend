package backend.com.parcelsystem.Service;

import java.util.List;

import backend.com.parcelsystem.Models.Driver;

public interface DriverService {
    Driver getById(Long id);
    Driver getByAuthenticatedUser();
    List<Driver> getDriversByCity(String city);
}
