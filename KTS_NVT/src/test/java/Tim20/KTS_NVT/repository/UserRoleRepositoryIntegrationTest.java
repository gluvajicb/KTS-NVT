package Tim20.KTS_NVT.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.h2.engine.SysProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.UserRole;
import tim20.KTS_NVT.repository.UserRoleRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRoleRepositoryIntegrationTest {

	@Autowired
	private UserRoleRepository roleRepository;
	
	@Test
	public void findByRoleTest() {
		UserRole user = roleRepository.findByRole("USER");
		 
		assertNotNull(user);
		assertEquals("USER", user.getRole());
		
		UserRole admin = roleRepository.findByRole("ADMIN");
		 
		assertNotNull(admin);
		assertEquals("ADMIN", admin.getRole());

	}
}
