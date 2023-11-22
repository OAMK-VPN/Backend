package backend.com.parcelsystem.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Sender;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;

@Repository
public interface ParcelRepos extends JpaRepository<Parcel, Long> {

    List<Parcel> findByReceiver(Receiver receiver);

    List<Parcel> findBySender(Sender sender);

    List<Parcel> findByStatus(ParcelStatus status);

    List<Parcel> findByStatusAndCity(ParcelStatus status, String city);
    
    List<Parcel> findByStatusAndDriver(ParcelStatus status, Driver driver);

    Optional<Parcel> findByTrackingNumber(String trackingNumber);

    List<Parcel> findByCabinet(Cabinet cabinet);

    @Query("SELECT p FROM Parcel p WHERE p.pickupExpiry < :currentDateTime AND p.status = :parcelStatus")
    List<Parcel> findExpiredParcelsByStatus( @Param("currentDateTime") LocalDateTime currentDateTime, @Param("parcelStatus") ParcelStatus parcelStatus
    );
}