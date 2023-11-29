package backend.com.parcelsystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Locker;

@Repository
public interface CabinetRepos extends JpaRepository<Cabinet, Long> {

    List<Cabinet> findByLocker(Locker locker);
    List<Cabinet> findByLockerAndEmptyIsTrue(Locker locker);
    List<Cabinet> findByLockerAndEmptyIsFalse(Locker locker);
    List<Cabinet> findByEmptyIsTrue();
    @Query("SELECT c FROM Cabinet c WHERE c.locker.city = :city AND c.empty = true")
    List<Cabinet> findEmptyCabinetsByCity(@Param("city") String city);
} 