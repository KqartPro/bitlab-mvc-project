package kz.pryahin.SpringBootTwo.repositories;

import jakarta.transaction.Transactional;
import kz.pryahin.SpringBootTwo.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
