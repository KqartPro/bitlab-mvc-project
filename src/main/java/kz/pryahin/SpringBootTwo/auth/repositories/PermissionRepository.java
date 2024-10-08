package kz.pryahin.SpringBootTwo.auth.repositories;

import jakarta.transaction.Transactional;
import kz.pryahin.SpringBootTwo.auth.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
