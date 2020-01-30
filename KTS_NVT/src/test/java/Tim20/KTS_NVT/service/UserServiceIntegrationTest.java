package Tim20.KTS_NVT.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tim20.KTS_NVT.dto.UserDTO;
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.security.UserTokenState;
import tim20.KTS_NVT.service.UserService;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Test
    public void findAll() {
        List<User> users = userService.findAll();

        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @Test
    public void findById() {
        long id = 1;
        User user = userService.findById(id);

        assertNotNull(user);
        assertEquals(id, user.getId());
    }

    @Test
    public void findByIdNotFound() {
        long id = 2;
        User user = userService.findById(id);

        assertNull(user);
    }

    @Test
    public void login() {
        UserDTO dto = new UserDTO("vpetrovic", "test123");
        UserTokenState token = userService.loginUser(dto);

        assertNotNull(token);
    }

    @Test
    public void loginWrongCredentials() {
        UserDTO dto = new UserDTO("perke", "asdfasd");
        UserTokenState token = userService.loginUser(dto);

        assertNull(token);
    }

    @Test
    public void register() {
        UserDTO dto = new UserDTO("perica", "test123", "test123",
            "Petar", "Markovic", "vule97+petar@gmail.com");

        boolean response = userService.registerUser(dto);
        assertTrue(response);
    }

    @Test
    public void verify() {
        boolean response = userService.verifyAccount("vule97+petar@gmail.com", "de4939e0-df55-4289-a7e8-8009309680df");
        assertTrue(response);
    }
}
