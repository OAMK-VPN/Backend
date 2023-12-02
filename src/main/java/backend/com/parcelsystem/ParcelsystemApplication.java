package backend.com.parcelsystem;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.Driver;
import backend.com.parcelsystem.Models.Parcel;
import backend.com.parcelsystem.Models.Receiver;
import backend.com.parcelsystem.Models.Sender;
import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Models.Enums.ParcelStatus;
import backend.com.parcelsystem.Repository.CabinetRepos;
import backend.com.parcelsystem.Repository.CodeRepos;
import backend.com.parcelsystem.Repository.DriverRepos;
import backend.com.parcelsystem.Repository.LockerRepos;
import backend.com.parcelsystem.Repository.ParcelRepos;
import backend.com.parcelsystem.Repository.ReceiverRepos;
import backend.com.parcelsystem.Repository.SenderRepos;
import backend.com.parcelsystem.Repository.UserRepos;
import backend.com.parcelsystem.Utils.SampleGenerator;
import backend.com.parcelsystem.Utils.BulkUsersGenerator;

@SpringBootApplication
public class ParcelsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelsystemApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepos userRepos, SampleGenerator sampleGenerator, DriverRepos driverRepos,
			ReceiverRepos receiverRepos, SenderRepos senderRepos, ParcelRepos parcelRepos, CodeRepos codeRepos,
			LockerRepos lockerRepos, CabinetRepos cabinetRepos) {
		return args -> {

			Users quan = sampleGenerator.generateUser("quan", "quan@gmail.com", "Quan Doan", "OULU", "yliopistokatu 2",
					"90570");
			Users Peter = sampleGenerator.generateUser("peter", "peter@gmail.com", "Peter senull00", "OULU", "Tehtaankatu 3",
					"90130");
			Users hang = sampleGenerator.generateUser("hang", "hang@gmail.com", "hang", "HELSINKI", "Malminkaari 19",
					"00700");
			Users kwang = sampleGenerator.generateUser("kwang", "kwang@gmail.com", "kwang", "HELSINKI", "kalteentie 2",
					"00770");

			Users robot = sampleGenerator.generateUser("robot", "robot@gmail.com", "robot", "HELSINKI", "kalteentie 2",
					"00770");	
					
			//Quan asked to add around 20 random users to the database. After being run, the users' passwords are stored in SignUpJsonData.json
			FileWriter fileWriter = new FileWriter("SignUpJsonData.json");
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
	        BulkUsersGenerator.bulkUserSignUp(20); 
			
			
			Driver robotdriver = new Driver(robot);
			driverRepos.findByUser(robot).orElseGet(() -> driverRepos.save(robotdriver));


			Driver Kwangdriver = new Driver(kwang);
			driverRepos.findByUser(kwang).orElseGet(() -> driverRepos.save(Kwangdriver));

			Sender peterCustomer = new Sender(Peter);
			senderRepos.findByUser(Peter).orElseGet(() -> senderRepos.save(peterCustomer));

			Receiver hangReceiver = new Receiver(hang);
			receiverRepos.findByUser(hang).orElseGet(() -> receiverRepos.save(hangReceiver));

			// Parcel parcel = new Parcel(UUID.randomUUID().toString(),
			// ParcelStatus.IN_DELIVERY, "HELSINKI" ,"Kalteentie 2", "00770", hangReceiver,
			// peterCustomer, 5, 0.4, 0.4);
			// parcel.setDriver(Kwangdriver);
			// Optional<Cabinet> cabinetEntity = cabinetRepos.findById(187L);
			// Cabinet cabinet = cabinetEntity.get();

			// parcel.setCabinet(cabinet);
			// parcel.setPickupAvailability(false);

			// parcelRepos.save(parcel);

		};
	}
}
