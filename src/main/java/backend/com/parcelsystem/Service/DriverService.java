package backend.com.parcelsystem.Service;

import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Request.UserSignIn;
import backend.com.parcelsystem.Models.Request.UserSignUp;
import backend.com.parcelsystem.Models.Response.AuthResponse;

public interface DriverService {
    Driver getById(Long id);
    Driver getByAuthenticatedUser();

    AuthResponse saveDriver(DriverSignUp userSignup);
    AuthResponse signIn(DriverSignIn userSignIn);
}