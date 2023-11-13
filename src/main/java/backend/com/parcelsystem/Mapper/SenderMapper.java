package backend.com.parcelsystem.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Sender;
import backend.com.parcelsystem.Models.Response.DriverResponse;
import backend.com.parcelsystem.Models.Response.SenderResponse;

@Component
public class SenderMapper {
    @Autowired    
    ModelMapper modelMapper;
    @Autowired
    UserMapper userMapper;

    public SenderResponse mapSenderToResponse(Sender sender) {
        SenderResponse res = modelMapper.map(sender, SenderResponse.class);
        res.setUser(userMapper.mapUserToResponse(sender.getUser()));
        return res;
    }
}
