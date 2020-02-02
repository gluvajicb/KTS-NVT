package Tim20.KTS_NVT.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import tim20.KTS_NVT.dto.UserDTO;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.repository.SectorRepository;
import tim20.KTS_NVT.repository.UserRepository;
import tim20.KTS_NVT.security.UserTokenState;
import tim20.KTS_NVT.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepositoryMocked;

    @Before
    public void setUp() {
        User user = new User(1l, "perica", "test123", "Petar", "Markovic", "vule97+petar@gmail.com", "065 353 97 23", true, "verifToken", null);
        Optional<User> optionalUser = Optional.of(user);
        Mockito.when(userRepositoryMocked.findById(user.getId())).thenReturn(optionalUser);

        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepositoryMocked.findAll()).thenReturn(users);
    }

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
                "Petar", "Markovic", "vule97+petar@gmail.com", "065 353 97 23");

        boolean response = userService.registerUser(dto);
        assertTrue(response);
    }

    @Test
    public void verify() {
        boolean response = userService.verifyAccount("vule97+petar@gmail.com", "de4939e0-df55-4289-a7e8-8009309680df");
        assertTrue(response);
    }
}
