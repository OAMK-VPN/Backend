package backend.com.parcelsystem.Repository;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Locker;

@Repository
public interface LockerRepos extends JpaRepository<Locker, Long>{
    List<Locker> findByZipcode(String zipcode);
    List<Locker> findByCity(String city);
}
