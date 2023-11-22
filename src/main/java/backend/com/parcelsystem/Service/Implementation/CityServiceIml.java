package backend.com.parcelsystem.Service.Implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.com.parcelsystem.Models.City;
import backend.com.parcelsystem.Repository.CityRepos;
import backend.com.parcelsystem.Service.CityService;

@Service
public class CityServiceIml  implements CityService{

    @Autowired
    CityRepos cityRepos;
    
    @Override
    public List<City> getAll() {
        return cityRepos.findAll();
    }
}
