package backend.com.parcelsystem.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import backend.com.parcelsystem.Mapper.DriverMapper;
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Response.DriverResponse;
import backend.com.parcelsystem.Service.DriverService;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {
    @Autowired
    DriverMapper driverMapper;
    @Autowired
    DriverService driverService;


    // get driver by id (add bearer token to request)
    @GetMapping("/driver/id/{id}")
    public ResponseEntity<DriverResponse> getById(@PathVariable Long id) {
        DriverResponse res = driverMapper.mapDriverToResponse(driverService.getById(id));
        return new ResponseEntity<DriverResponse>(res, HttpStatus.OK);
    }

    // get driver by authenticated user or if the driver is not existent, the method will automatically create new driver account for the current authenticated user (add bearer token to request)
    @GetMapping("/driver/authenticated")
    public ResponseEntity<DriverResponse> getByAuthUser() {
        DriverResponse res = driverMapper.mapDriverToResponse(driverService.getByAuthenticatedUser());
        return new ResponseEntity<DriverResponse>(res, HttpStatus.OK);
    }

    // get driver by city (add bearer token to request)
    @GetMapping("/city/{city}")
    public ResponseEntity<List<DriverResponse>> getByCity(@PathVariable String city) {
        List<Driver> drivers = driverService.getDriversByCity(city);
        List<DriverResponse> res = drivers.stream().map(driver ->  driverMapper.mapDriverToResponse(driver)).collect(Collectors.toList());
        return new ResponseEntity<List<DriverResponse>>(res, HttpStatus.OK);
    }
}
