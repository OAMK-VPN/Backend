package  backend.com.parcelsystem.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  backend.com.parcelsystem.Models.Notification;
import backend.com.parcelsystem.Models.Receiver;

@Repository
public interface NotificationRepos extends JpaRepository<Notification, Long> {
    Notification findByReceiver(Receiver receiver);
    boolean existsByReceiver(Receiver receiver);
    
    
}