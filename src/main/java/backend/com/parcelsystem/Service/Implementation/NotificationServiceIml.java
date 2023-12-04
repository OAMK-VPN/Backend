package backend.com.parcelsystem.Service.Implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Service.ReceiverService;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import backend.com.parcelsystem.Service.NotificationService;
import backend.com.parcelsystem.Models.Notification;
import backend.com.parcelsystem.Repository.NotificationRepos;

@Service
public class NotificationServiceIml implements NotificationService {
    
    @Autowired
    private JavaMailSender mailSender;


    @Autowired
    private NotificationRepos notificationRepos;

    @Autowired
    private ReceiverService receiverService;

    public void saveNotification(Parcel parcel) {
        if (!notificationRepos.existsByReceiver(parcel.getReceiver()) ) {
            Notification notification = new Notification();
            notification.setReceiver(parcel.getReceiver());
            notification.setRead(false);
            notification.setDateCreated(parcel.getDateCreated());
            notification.setDateUpdated(parcel.getDateUpdated());
            notificationRepos.save(notification);
        } else {
            Notification notification = notificationRepos.findByReceiver(parcel.getReceiver());
            notification.setRead(false);
            notification.setDateCreated(parcel.getDateCreated());
            notification.setDateUpdated(parcel.getDateUpdated());
            notificationRepos.save(notification);
        }

        
    }

    public void updateNotificationStatusWhenDriverDropOff(Parcel parcel) {
        
        Notification notification = notificationRepos.findByReceiver(parcel.getReceiver());
        notification.setRead(!notification.isRead());
        notificationRepos.save(notification);
    }

    @Override
    public void switchNotificationStatus(){
        Receiver receiver= receiverService.getByAuthenticatedUser();
        Notification notification = notificationRepos.findByReceiver(receiver);
        if (notification.isRead()) notification.setRead(!notification.isRead());
        else notification.setRead(!notification.isRead());
        notificationRepos.save(notification);

    }

    @Override
    public void setNotificationAsRead(){
        Receiver receiver= receiverService.getByAuthenticatedUser();
        Notification notification = notificationRepos.findByReceiver(receiver);
        notification.setRead(!notification.isRead());
        notificationRepos.save(notification);

    }

    @Override
    public void sendNotification(Parcel parcel) {
        SimpleMailMessage message = new SimpleMailMessage();

        String commonNotificationPartForReceiver= "\n"+"status: "+parcel.getStatus()+"\n"+"tracking number: "+parcel.getTrackingNumber()+
            "\n"+"parcel sender: "+parcel.getSender().getUser().getFullname()+
            "\n"+"date created: "+parcel.getDateCreated()+"\n"+"date updated: "+parcel.getDateUpdated();
        String commonNotificationPartForSender= "\n"+"status: "+parcel.getStatus()+"\n"+"tracking number: "+parcel.getTrackingNumber()+
            "\n"+"parcel receiver: "+parcel.getReceiver().getUser().getFullname()+
            "\n"+"date created: "+parcel.getDateCreated()+"\n"+"date updated: "+parcel.getDateUpdated();

        if ( parcel.getStatus().equals(ParcelStatus.WAITING_FOR_SENDER) ) {
        // Send notification to the receiver (excludes the code from the notification).
            message.setTo(parcel.getReceiver().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear receiver "+parcel.getReceiver().getUser().getFullname()+", A parcel was created for you."+
            commonNotificationPartForReceiver);
            mailSender.send(message); saveNotification(parcel);
            // Send notification to sender contains the parcel, the cabinet, the locker, the code to open cabinet in order that the sender can drop the parcel into the cabinet (include the code in notification for sender)
            message.setTo(parcel.getSender().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear sender "+parcel.getSender().getUser().getFullname()+", A cabinet was reserved for you. Please go to the below address to drop off your parcel with the one-time code: "+
            "\n"+"one-time code: "+parcel.getCabinet().getCode().getCode()+
            "\n"+"cabinet #: "+parcel.getCabinet().getNum()+" locker #: "+parcel.getCabinet().getLocker().getId()+" locker name: "+parcel.getCabinet().getLocker().getName()+
            "\n"+"address: "+parcel.getCabinet().getLocker().getAddress()+" zipcode: "+parcel.getCabinet().getLocker().getZipcode()+" city: "+parcel.getCabinet().getLocker().getCity()+
            commonNotificationPartForSender);
            mailSender.send(message);


        }

        else if ( parcel.getStatus().equals(ParcelStatus.WAITING_FOR_DRIVER) ) {
            //if sender drops off -> send notification to the receiver (note -> exclude the code from the notification for receiver)
            message.setTo(parcel.getReceiver().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear receiver "+parcel.getReceiver().getUser().getFullname()+", your parcel was already dropped off by the sender."+"\n"+"date created: "+parcel.getDateCreated()+
            commonNotificationPartForReceiver);
            mailSender.send(message); 
        }

        else if (parcel.getStatus().equals(ParcelStatus.IN_DELIVERY)) {
            // send notification to the receiver (note -> exclude the code from the notification for receiver)
            message.setTo(parcel.getReceiver().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear receiver "+parcel.getReceiver().getUser().getFullname()+", your parcel is now in delivery."+
            commonNotificationPartForReceiver);
            mailSender.send(message); 
            // send notification to the sender (note -> exclude the code from the notification for receiver) 
            message.setTo(parcel.getSender().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear sender "+parcel.getSender().getUser().getFullname()+", your parcel is now in delivery."+
            commonNotificationPartForSender);
            mailSender.send(message);
        }

        
        else if (parcel.getStatus().equals(ParcelStatus.WAITING_FOR_RECEIVER)) {
            // if driver drops off -> send to notification to the receiver (note -> include the code in the notification for receiver) 
            message.setTo(parcel.getReceiver().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear receiver "+parcel.getReceiver().getUser().getFullname()+", your parcel is now ready for your pick-up."+
            "\n"+"status: "+parcel.getStatus()+"\n"+"tracking number: "+parcel.getTrackingNumber()+
            "\n"+"pick-up code: "+parcel.getCabinet().getCode().getCode()+
            "\n"+"parcel sender: "+parcel.getSender().getUser().getFullname()+
            "\n"+"date created: "+parcel.getDateCreated()+"\n"+"date updated: "+parcel.getDateUpdated());
            mailSender.send(message); updateNotificationStatusWhenDriverDropOff(parcel);
            // send notification  to the sender (note -> exclude the code from the notification for sender)
            message.setTo(parcel.getSender().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear sender "+parcel.getSender().getUser().getFullname()+", your parcel was already dropped off by the driver. The parcel is ready for receiver's pickup."+
            commonNotificationPartForSender);
            mailSender.send(message);
        }
        
        else if (parcel.getStatus().equals(ParcelStatus.PICKED_UP_BY_RECIEVER) ) {
            message.setTo(parcel.getReceiver().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear receiver "+parcel.getReceiver().getUser().getFullname()+", your parcel was already picked up by you."+
            commonNotificationPartForReceiver);
            mailSender.send(message); 
              // send notification to the sender (note -> exclude the code from the notification for receiver) 
            message.setTo(parcel.getSender().getUser().getEmail());
            message.setSubject("POSTI - Parcel Notification");
            message.setText("Dear sender "+parcel.getSender().getUser().getFullname()+", your parcel was already picked up by the receiver."+
            commonNotificationPartForSender);
            mailSender.send(message);
    }

    }

}

