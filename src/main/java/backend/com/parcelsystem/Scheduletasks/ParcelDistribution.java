package backend.com.parcelsystem.Scheduletasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Service.ParcelService;

@Configuration
@EnableScheduling
public class ParcelDistribution {
    
    @Autowired
    ParcelService parcelService;

    // @Scheduled(fixedRate = 2000)
    // public void scheduleFixedRateTask() {
    //     System.out.println(
    //     "Fixed rate task - " + System.currentTimeMillis() / 1000);
    // }

    // @Scheduled(cron = "0 0 3 * * *") // Run at 3 am every day
    // public void generateParcelsJob() {
    //     parcelService.CheckAllSendExpiredParcels();
    //     parcelService.CheckAllPickupExpiredParcels();
    //     parcelService.assignAllParcelsToDrivers();
    //     parcelService.generateParcelsAndSendToDriversByRobot();
    // }
}
