package tim20.KTS_NVT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim20.KTS_NVT.model.UserRole;

public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);
}