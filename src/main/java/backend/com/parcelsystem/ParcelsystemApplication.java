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
			
			
			Users quan = sampleGenerator.generateUser("quan", "quan@gmail.com", "Quan Doan", "OULU", "yliopistokatu 2", "90570");


			Users Peter = sampleGenerator.generateUser("peter", "peter@gmail.com", "Peter senull00", "OULU", "Tehtaankatu 3", "90130");

			Users hang = sampleGenerator.generateUser("hang", "hang@gmail.com", "hang", "HELSINKI", "Malminkaari 19", "00700");

			Users kwang = sampleGenerator.generateUser("kwang", "kwang@gmail.com", "kwang", "HELSINKI", "kalteentie 2", "00770");

			


		};
	}
}
