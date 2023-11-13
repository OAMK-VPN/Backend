package backend.com.parcelsystem.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Receiver;
import  backend.com.parcelsystem.Models.Users;

@Repository
public interface ReceiverRepos extends JpaRepository<Receiver, Long> {
    Optional<Receiver> findByUser(Users user);
}