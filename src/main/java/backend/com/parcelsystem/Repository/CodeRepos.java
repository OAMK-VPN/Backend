package backend.com.parcelsystem.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import backend.com.parcelsystem.Models.Code;

@Repository
public interface CodeRepos extends JpaRepository<Code, Long> {
    boolean existsByCode(String code);
    Optional<Code> findByCabinetId(Long cabinetId);
}
