package tim20.KTS_NVT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.model.UserRole;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByEmailAndVerificationToken(String email, String verificationToken);
    List<User> findByRolesContains(UserRole role);
}
