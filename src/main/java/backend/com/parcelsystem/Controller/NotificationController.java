package backend.com.parcelsystem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Mapper.NotificationMapper;

import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Response.NotificationResponse;

import backend.com.parcelsystem.Repository.NotificationRepos;
import backend.com.parcelsystem.Service.NotificationService;
import backend.com.parcelsystem.Service.ReceiverService;


@RestController
public class NotificationController {

    
    @Autowired
    NotificationService notificationService;
    @Autowired
    NotificationRepos notificationRepos;

    @Autowired
    NotificationMapper notificationMapper;
    @Autowired
    ReceiverService receiverService;

    @GetMapping("/users/notification_status")
    public ResponseEntity<NotificationResponse> getNotificationStatus() {
        Receiver receiver = receiverService.getByAuthenticatedUser();
        NotificationResponse res = notificationMapper.mapNotificationToResponse(notificationRepos.findByReceiver(receiver) != null ? notificationRepos.findByReceiver(receiver)  : null);
        return new ResponseEntity<NotificationResponse>(res, HttpStatus.OK);
    }

    @PutMapping("/users/notification_status")
    public ResponseEntity<NotificationResponse> updateNotificationStatus() {
        notificationService.switchNotificationStatus();
        Receiver receiver = receiverService.getByAuthenticatedUser();
        NotificationResponse res = notificationMapper.mapNotificationToResponse(notificationRepos.findByReceiver(receiver));
        return new ResponseEntity<NotificationResponse>(res, HttpStatus.OK);
    }
    
}
