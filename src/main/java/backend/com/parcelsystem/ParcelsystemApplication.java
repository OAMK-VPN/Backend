package backend.com.parcelsystem;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import backend.com.parcelsystem.Models.Users;
import backend.com.parcelsystem.Repository.UserRepos;
import backend.com.parcelsystem.Utils.SampleGenerator;

@SpringBootApplication
public class ParcelsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParcelsystemApplication.class, args);
	}

		@Bean
	CommandLineRunner commandLineRunner(UserRepos userRepos, SampleGenerator sampleGenerator) {
		return args -> {
			
			
			Users quan = sampleGenerator.generateUser("quan", "quan@gmail.com", "quan doan", "oulu", "yliopistokatu 2", "90570");

			


		};
	}
}
