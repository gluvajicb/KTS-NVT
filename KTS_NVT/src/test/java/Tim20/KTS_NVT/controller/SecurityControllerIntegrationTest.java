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
    public void login_success() {
        ResponseEntity<UserTokenState> responseEntity = restTemplate.postForEntity("/security/login",
                new UserDTO("vpetrovic", "test123", null, null, null, null), UserTokenState.class);

        UserTokenState tokenState = responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(tokenState);
    }

    @Test
    public void login_no_data() {
        ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/security/login",
                new UserDTO("", null, null, null, null, null), Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("All fields must be provided.", error.getMessage());
    }

    @Test
    public void login_wrong_credentials() {
        ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/security/login",
                new UserDTO("vpetrovic", "password", null, null, null, null), Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Provided credentials don't match with any account.", error.getMessage());
    }
}
