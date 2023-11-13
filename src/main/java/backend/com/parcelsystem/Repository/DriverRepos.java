package backend.com.parcelsystem.Repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Driver;
import  backend.com.parcelsystem.Models.Users;

@Repository
public interface DriverRepos extends JpaRepository<Driver, Long> {
    Optional<Driver> findByUser(Users user);
}