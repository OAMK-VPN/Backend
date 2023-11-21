package backend.com.parcelsystem.Service;

import backend.com.parcelsystem.Models.Driver;

import backend.com.parcelsystem.Models.Response.AuthResponse;

import backend.com.parcelsystem.Models.Request.DriverSignUp;
import backend.com.parcelsystem.Models.Request.DriverSignIn;

public interface DriverService {
    Driver getById(Long id);
    // Driver getByAuthenticatedUser();
    Driver getDriverByUsername(String username);
    Driver getAuthUser();

    AuthResponse saveDriver(DriverSignUp driverSignup);
    AuthResponse signIn(DriverSignIn driverSignIn);
}
