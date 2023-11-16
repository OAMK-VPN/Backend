package backend.com.parcelsystem.Service.Implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Exception.EntityNotFoundException;
import backend.com.parcelsystem.Models.Locker;
import backend.com.parcelsystem.Repository.LockerRepos;
import backend.com.parcelsystem.Service.LockerService;

@Service
public class LockerServiceIml implements LockerService {
    @Autowired
    LockerRepos lockerRepos;

    @Override
    public List<Locker> getAll() {
        return lockerRepos.findAll();
    }

    @Override
    public List<Locker> getByZipcode(String zipcode) {
        return lockerRepos.findByZipcode(zipcode);
    }

    @Override
    public List<Locker> getByCity(String city) {
        return lockerRepos.findByCity(city.toUpperCase());
    }

    @Override
    public Locker getById(Long id) {
        // return lockerRepos.findById(id).orElse(null);
        Optional<Locker> entity = lockerRepos.findById(id);
        if(!entity.isPresent()) {
            throw new EntityNotFoundException("the locker not found");
        }
        return entity.get();
    }
}
