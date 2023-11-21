package backend.com.parcelsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Mapper.DriverMapper;
import backend.com.parcelsystem.Models.Request.DriverSignIn;
import backend.com.parcelsystem.Models.Request.DriverSignUp;
import backend.com.parcelsystem.Models.Response.AuthResponse;
import backend.com.parcelsystem.Models.Response.DriverResponse;
import backend.com.parcelsystem.Service.DriverService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/driver")
public class DriverController {
    @Autowired
    DriverMapper driverMapper;
    @Autowired
    DriverService driverService;

    @PutMapping("/signIn")
    public ResponseEntity<AuthResponse> signIn(@Valid @RequestBody DriverSignIn driverSignIn) {
        return new ResponseEntity<AuthResponse>(driverService.signIn(driverSignIn), HttpStatus.OK);
    }
    //requires token
    @GetMapping("/authUser/getAuthUser")
    public ResponseEntity<DriverResponse> getAuthUser() {
        DriverResponse res = driverMapper.mapDriverToResponse(driverService.getAuthUser());
        return new ResponseEntity<DriverResponse>(res, HttpStatus.OK);
    }
    
    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody DriverSignUp driverSignup) {
        return new ResponseEntity<AuthResponse>(driverService.saveDriver(driverSignup), HttpStatus.CREATED);
    }


    // get driver by id (add bearer token to request)
    @GetMapping("/driver/id/{id}")
    public ResponseEntity<DriverResponse> getById(@PathVariable Long id) {
        DriverResponse res = driverMapper.mapDriverToResponse(driverService.getById(id));
        return new ResponseEntity<DriverResponse>(res, HttpStatus.OK);
    }

    // get driver by authenticated user or if the driver is not existent, the method will automatically create new driver account for the current authenticated user (add bearer token to request)
    /* @GetMapping("/driver/authenticated")
    public ResponseEntity<DriverResponse> getByAuthUser() {
        DriverResponse res = driverMapper.mapDriverToResponse(driverService.getByAuthenticatedUser());
        return new ResponseEntity<DriverResponse>(res, HttpStatus.OK);
    } */
}
