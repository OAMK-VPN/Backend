package backend.com.parcelsystem.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Response.ReceiverResponse;

@Component
public class ReceiverMapper {
    @Autowired    
    ModelMapper modelMapper;
    @Autowired
    UserMapper userMapper;

    public ReceiverResponse mapReceiverToResponse(Receiver receiver) {
        ReceiverResponse res = modelMapper.map(receiver, ReceiverResponse.class);
        res.setUser(userMapper.mapUserToResponse(receiver.getUser()));
        return res;
    }
}
