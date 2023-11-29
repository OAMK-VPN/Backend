package backend.com.parcelsystem.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Receiver;
import  backend.com.parcelsystem.Models.Users;

@Repository
public interface ReceiverRepos extends JpaRepository<Receiver, Long> {
    Optional<Receiver> findByUser(Users user);
    @Query(value = "SELECT r.* FROM receiver r " +
                   "JOIN users u ON r.user_id = u.id " +
                   "WHERE u.city = :city", nativeQuery = true)
    List<Receiver> findReceiversByCity(@Param("city") String city);
}