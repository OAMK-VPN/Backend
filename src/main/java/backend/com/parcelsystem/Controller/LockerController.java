package backend.com.parcelsystem.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.com.parcelsystem.Models.Locker;
import backend.com.parcelsystem.Service.LockerService;

@RestController
@RequestMapping("/api/lockers")
public class LockerController {
    private final LockerService lockerService;

    public LockerController(LockerService lockerService) {
        this.lockerService = lockerService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Locker>> getAllLockers() {
        List<Locker> lockers = lockerService.getAll();
        return new ResponseEntity<>(lockers, HttpStatus.OK);
    }

    @GetMapping("/zipcode/{zipcode}")
    public ResponseEntity<List<Locker>> getLockersByZipcode(@PathVariable String zipcode) {
        List<Locker> lockers = lockerService.getByZipcode(zipcode);
        return new ResponseEntity<>(lockers, HttpStatus.OK);
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<Locker>> getLockersByCity(@PathVariable String city) {
        List<Locker> lockers = lockerService.getByCity(city);
        return new ResponseEntity<>(lockers, HttpStatus.OK);
    }

    @GetMapping("/locker/{id}")
    public ResponseEntity<Locker> getLockerById(@PathVariable Long id) {
        Locker locker = lockerService.getById(id);
        return new ResponseEntity<>(locker, HttpStatus.OK);
    }
}
