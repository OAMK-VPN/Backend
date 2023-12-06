package backend.com.parcelsystem.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import backend.com.parcelsystem.Models.Cabinet;
import backend.com.parcelsystem.Models.City;
import backend.com.parcelsystem.Models.Code;
import backend.com.parcelsystem.Models.Locker;
import backend.com.parcelsystem.Repository.CabinetRepos;
import backend.com.parcelsystem.Repository.CityRepos;
import backend.com.parcelsystem.Repository.CodeRepos;
import backend.com.parcelsystem.Repository.LockerRepos;
import backend.com.parcelsystem.Service.CodeService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Objects;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    LockerRepos parcelLockerRepository;
    @Autowired
    CodeService codeService;
    @Autowired
    CodeRepos codeRepos;
    @Autowired
    CabinetRepos cabinetRepos;
    @Autowired
    CityRepos cityRepos;
    @Autowired
    LockerRepos lockerRepos;

    @Override
    public void run(String... args) throws Exception {
        // Read JSON data from file
        try {

            InputStream inputStream = Objects.requireNonNull(
                    DataLoader.class.getClassLoader().getResourceAsStream("/PostNordLocation.json"),
                    "PostNordLocation.json not found in classpath"
            );

            ObjectMapper objectMapper = new ObjectMapper();
            List<Locker> parcelLockers = objectMapper.readValue(inputStream,  objectMapper.getTypeFactory().constructCollectionType(List.class, Locker.class));

            // Save data to the database
            if (!lockerRepos.existsById(1L)) {
            parcelLockerRepository.saveAll(parcelLockers);
            generateAndSaveRandomCabinets();}

            // Close the InputStream when done
            inputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        
        }




    }

    private void generateAndSaveRandomCabinets() {
        List<Locker> parcelLockers = parcelLockerRepository.findAll();

        for (Locker parcelLocker : parcelLockers) {
            // add city to the database
            if (!cityRepos.existsByName(parcelLocker.getCity())) {
                cityRepos.save(new City(parcelLocker.getCity()));
            }

            List<Cabinet> cabinets = new ArrayList<>();

            for (int i = 1; i <= 5; i++) {
                Cabinet cabinet = new Cabinet();
                cabinet.setNum(i);
                cabinet.setWeigh(generateRandomWeight());
                cabinet.setHeigh(generateRandomHeight());
                cabinet.setWidth(generateRandomWidth());
                cabinet.setLength(generateRandomLength());
                cabinet.setLocker(parcelLocker);
                cabinet.setEmpty(true);
                cabinet.setFilled(false);

                // Save cabinet
                cabinetRepos.save(cabinet);
                cabinets.add(cabinet);

                // Generate and save random code for the cabinet
                Code code = new Code();
                code.setCode(codeService.generateRandomCode(cabinet.getLocker().getId()));
                code.setLocker(parcelLocker);
                code.setCabinet(cabinet);
                codeRepos.save(code);

                // Set the generated code to the cabinet
                // cabinet.setCode(code);
                // cabinetRepos.save(cabinet);
            }

            // Set the generated cabinets to the parcel locker
            parcelLocker.setCabinets(cabinets);
            parcelLockerRepository.save(parcelLocker);
        }
    }

    private double generateRandomWeight() {
        return 1 + new Random().nextDouble() * 9; // Random weight between 1 and 10 kg
    }

    private double generateRandomHeight() {
        return 0.3 + new Random().nextDouble() * 0.2; // Random height between 0.3 and 0.5 m
    }

    private double generateRandomWidth() {
        return (0.3 + new Random().nextDouble() * 0.2); // Random width between 0.3 and 0.5 m
    }

    private double generateRandomLength() {
        return (0.3 + new Random().nextDouble() * 0.2); // Random width between 0.3 and 0.5 m
    }

}
