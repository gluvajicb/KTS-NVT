package Tim20.KTS_NVT.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import tim20.KTS_NVT.dto.UserDTO;
import tim20.KTS_NVT.security.UserTokenState;
import tim20.KTS_NVT.service.UserService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Test
    public void loginSuccess() {
        ResponseEntity<UserTokenState> responseEntity = restTemplate.postForEntity("/security/login",
                new UserDTO("vpetrovic", "test123", null, null, null, null, null), UserTokenState.class);

        UserTokenState tokenState = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(tokenState);
    }

    @Test
    public void loginNoData() {
        ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/security/login",
                new UserDTO("", null, null, null, null, null, null), Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("All fields must be provided.", error.getMessage());
    }

    @Test
    public void loginWrongCredentials() {
        ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/security/login",
                new UserDTO("vpetrovic", "password", null, null, null, null, null), Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Provided credentials don't match with any account.", error.getMessage());
    }

    @Test
    public void registerNoData() {
        ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/security/register",
                new UserDTO("", "", "", "", "", "", ""), Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("All fields must be provided.", error.getMessage());
    }

    @Test
    public void registerUsernameTaken() {
        ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/security/register",
                new UserDTO("vpetrovic", "test123", "test123", "Petar", "Markovic", "perica@gmail.com", "065 353 97 23"), Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Username is taken, please choose another one.", error.getMessage());
    }

    @Test
    public void registerEmailInUse() {
        ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/security/register",
                new UserDTO("perica", "test123", "test123", "Petar", "Markovic", "vule97@gmail.com", "065 353 97 23"), Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Email is already in use, try logging in.", error.getMessage());
    }

    @Test
    public void registerPasswordsDontMatch() {
        ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/security/register",
                new UserDTO("perica", "test123", "Test123", "Petar", "Markovic", "vule97+petar@gmail.com", "065 353 97 23"), Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("Password confirmation is incorrect.", error.getMessage());
    }

    @Test
    public void registerSuccess() {
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity("/security/register",
                new UserDTO("perica", "test123", "test123", "Petar", "Markovic", "vule97+petar@gmail.com", "065 353 97 23"), Boolean.class);

        boolean response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(response);
    }

    @Test
    public void verifyNoData() {
        ResponseEntity<Error> responseEntity = restTemplate.getForEntity("/security/verify?email=test@gmail.com&token=", Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("All fields must be provided.", error.getMessage());
    }

    @Test
    public void verifyWrongData() {
        ResponseEntity<Error> responseEntity = restTemplate.getForEntity("/security/verify?email=test@gmail.com&token=klh52348reklaf3948y5kjsdf3487r", Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Provided verification data is not valid.", error.getMessage());
    }

    @Test
    public void verifySuccess() {
        ResponseEntity<Boolean> responseEntity = restTemplate.getForEntity("/security/verify?email=vule97%2Bpetar%40gmail.com&token=de4939e0-df55-4289-a7e8-8009309680df", Boolean.class);

        boolean response = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(response);
    }
}
