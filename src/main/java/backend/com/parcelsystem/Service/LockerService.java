package backend.com.parcelsystem.Service;

import java.util.List;

import backend.com.parcelsystem.Models.Locker;

public interface LockerService {
    List<Locker> getAll();
    List<Locker> getByZipcode(String zipcode);
    List<Locker> getByCity(String city);
    Locker getById(Long id);
} 