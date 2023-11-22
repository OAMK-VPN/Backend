package backend.com.parcelsystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.City;

@Repository
public interface CityRepos extends JpaRepository<City, Long> {
    boolean existsByName(String name);
    
} 