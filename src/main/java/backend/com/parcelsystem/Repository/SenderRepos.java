package backend.com.parcelsystem.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Sender;
import  backend.com.parcelsystem.Models.Users;

@Repository
public interface SenderRepos extends JpaRepository<Sender, Long> {
    Optional<Sender> findByUser(Users user);
}
