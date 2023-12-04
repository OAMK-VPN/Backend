package backend.com.parcelsystem.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import backend.com.parcelsystem.Models.Notification;
import backend.com.parcelsystem.Models.Response.NotificationResponse;


@Component
public class NotificationMapper {
    @Autowired
    ModelMapper modelMapper;

    public NotificationResponse mapNotificationToResponse(Notification notification) {
      if (notification == null) {
        return null;
      }
      NotificationResponse res = modelMapper.map(notification, NotificationResponse.class);
      return res;
    }

    
    
    
}
