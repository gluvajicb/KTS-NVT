package Tim20.KTS_NVT.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;







import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import tim20.KTS_NVT.converters.SectorDTOConverter;
import tim20.KTS_NVT.dto.LocationDTO;
import tim20.KTS_NVT.dto.SeatsTicketDTO;
import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.dto.StandTicketDTO;
import tim20.KTS_NVT.dto.TakenSeatsDTO;
import tim20.KTS_NVT.dto.TicketDTO;
import tim20.KTS_NVT.exceptions.LocationNotFoundException;
import tim20.KTS_NVT.exceptions.LowerThanZeroException;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.SeatsTicket;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.model.StandTicket;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.service.TicketService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TicketControllerIntegrationTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private TicketService ticketService;

	@Test
	public void getTicketsForEventTest() {

		ResponseEntity<TicketDTO[]> responseEntity = restTemplate.getForEntity("/tickets/4",
				TicketDTO[].class);

		TicketDTO[] tickets = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(2, tickets.length);
	}
	
	@Test
	public void getTicketsForEvent_EventNotFoundTest() {

		ResponseEntity<Error> responseEntity = restTemplate.getForEntity("/tickets/5", Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Event [5] not found", error.getMessage());
	}
	
	@Test
	public void deleteTicketNotFoundTest() {

		ResponseEntity<Error> responseEntity = restTemplate.exchange("/tickets/115", HttpMethod.DELETE,
				new HttpEntity<Object>(null), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Ticket [115] not found", error.getMessage());
	}
	
	/*@Test
	public void getTakenSeatsTest() {

		ResponseEntity<TakenSeatsDTO[]> responseEntity = restTemplate.getForEntity("/tickets/takenSeatsAllDays/4",
				TakenSeatsDTO[].class);

		TakenSeatsDTO[] tickets = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		System.out.println(tickets.length);
		}
/*
	
	@Test
	public void addSeatsTicketTest() {

		int size = ticketService.findAll().size();

		ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity("/add-seats-ticket",
				new SeatsTicketDTO(1l, 1l, true, 5000.00, 1, 1, 1l,false,"2020-03-03"), Boolean.class);

		System.out.println(responseEntity.getBody());
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		List<Ticket> tickets = ticketService.findAll();
		assertEquals(size + 1, tickets.size());
		
	}
	*/
	
	@Test
	public void addSeatsTicketEventNotFound() {

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/add-seats-ticket",
				new SeatsTicketDTO(5l, 1l, true, 5000.00, 1, 1, 1l, false, "2020-12-12"), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(0, error.getCode());
	}
	
	@Test
	public void addStandTicketEventNotFound() {

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/add-stand-ticket",
				new StandTicketDTO(5l, 1l, true, 5000.00, 1l, false, "2020-12-12"), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(0, error.getCode());
	}
	
	@Test
	public void addStandTicketSectorNotFound() {

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/add-stand-ticket",
				new StandTicketDTO(1l, 133l, true, 5000.00, 1l, false, "2020-12-12"), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(0, error.getCode());
	}
	
	@Test
	public void addSeatsTicketSectorNotFound() {

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/add-seats-ticket",
				new SeatsTicketDTO(1l, 1l, true, 5000.00, 1, 1, 5l, false, "2020-12-12"), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(0, error.getCode());
	}
}



