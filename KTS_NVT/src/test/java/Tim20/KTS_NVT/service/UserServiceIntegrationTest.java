package Tim20.KTS_NVT.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tim20.KTS_NVT.dto.UserDTO;
import tim20.KTS_NVT.exceptions.*;
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
        long id = 2;
        User user = userService.findById(id);

        assertNotNull(user);
        assertEquals(id, user.getId());
    }

    @Test
    public void findByIdNotFound() {
        long id = 398;
        User user = userService.findById(id);

        assertNull(user);
    }

    @Test
    public void login() {
        UserDTO dto = new UserDTO("vpetrovic", "test123");
        UserTokenState token = userService.loginUser(dto);

        assertNotNull(token);
    }

    @Test(expected = FieldsRequiredException.class)
    public void loginNoData() {
        UserDTO dto = new UserDTO(null, "");
        UserTokenState token = userService.loginUser(dto);

        assertNotNull(token);
    }

    @Test(expected = WrongCredentialsException.class)
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

    @Test(expected = FieldsRequiredException.class)
    public void registerNoData() {
        UserDTO dto = new UserDTO("", "", "", "", "", "", "");
        userService.registerUser(dto);
    }

    @Test(expected = UsernameTakenException.class)
    public void registerUsernameTaken() {
        UserDTO dto = new UserDTO("vpetrovic", "test123", "test123", "Petar", "Markovic", "perica@gmail.com", "065 353 97 23");
        userService.registerUser(dto);
    }

    @Test(expected = PasswordsNotMatchingException.class)
    public void registerPasswordsDontMatch() {
        UserDTO dto = new UserDTO("perica", "test123", "Test123", "Petar", "Markovic", "vule97+petar@gmail.com", "065 353 97 23");
        userService.registerUser(dto);
    }

    @Test(expected = EmailInUseException.class)
    public void registerEmailInUse() {
        UserDTO dto = new UserDTO("perica", "test123", "test123", "Petar", "Markovic", "vule97@gmail.com", "065 353 97 23");
        boolean response = userService.registerUser(dto);
    }

    @Test
    public void verify() {
        boolean response = userService.verifyAccount("vule97+petar@gmail.com", "de4939e0-df55-4289-a7e8-8009309680df");
        assertTrue(response);
    }

    @Test(expected = FieldsRequiredException.class)
    public void verifyNoData() {
        userService.verifyAccount("", null);
    }

    @Test(expected = WrongVerificationTokenAndEmail.class)
    public void verifyWrongData() {
        userService.verifyAccount("vule97+petar@gmail.com", "neki token");
    }
}
