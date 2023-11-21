package  backend.com.parcelsystem.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import  backend.com.parcelsystem.Models.Users;

@Repository
public interface UserRepos extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);  /* maybe here there could be a potential problem, better use findByUserName(String userName) with all the capital letters among them */
    Optional<Users> findByEmail(String email);
}
