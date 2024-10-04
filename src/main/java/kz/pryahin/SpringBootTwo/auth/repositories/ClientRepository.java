package kz.pryahin.SpringBootTwo.auth.repositories;

import jakarta.transaction.Transactional;
import kz.pryahin.SpringBootTwo.auth.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {
	Client findAllByEmail(String email);
}
