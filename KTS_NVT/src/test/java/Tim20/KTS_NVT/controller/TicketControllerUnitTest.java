package Tim20.KTS_NVT.controller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.service.TicketService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)

public class TicketControllerUnitTest {

	@Autowired
    private TestRestTemplate restTemplate;
	
	@MockBean
	private TicketService ticketServiceMocked;
	
	@Test
	public void addSeatsTicket_eventNotFound_NOT_FOUND () throws Exception {
		Long eventId = 155L;
    	int grade = 10;
	}
}
