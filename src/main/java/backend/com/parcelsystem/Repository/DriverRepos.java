package backend.com.parcelsystem.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Driver;
import  backend.com.parcelsystem.Models.Users;

@Repository
public interface DriverRepos extends JpaRepository<Driver, Long> {
    Optional<Driver> findByUser(Users user);

    @Query(value = "SELECT d.* FROM driver d " +
                   "JOIN users u ON d.user_id = u.id " +
                   "WHERE u.city = :city", nativeQuery = true)
    List<Driver> findDriversByCity(@Param("city") String city);
}