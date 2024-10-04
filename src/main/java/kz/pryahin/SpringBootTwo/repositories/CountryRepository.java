package kz.pryahin.SpringBootTwo.repositories;

import jakarta.transaction.Transactional;
import kz.pryahin.SpringBootTwo.entities.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface CountryRepository extends JpaRepository<Country, Long> {
}
