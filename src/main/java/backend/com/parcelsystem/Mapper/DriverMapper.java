package backend.com.parcelsystem.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Response.DriverResponse;
import backend.com.parcelsystem.Models.Response.ReceiverResponse;

@Component
public class DriverMapper {
    @Autowired    
    ModelMapper modelMapper;
    @Autowired
    UserMapper userMapper;

    public DriverResponse mapDriverToResponse(Driver driver) {
        DriverResponse res = modelMapper.map(driver, DriverResponse.class);
        res.setUser(userMapper.mapUserToResponse(driver.getUser()));
        return res;
    }
}
