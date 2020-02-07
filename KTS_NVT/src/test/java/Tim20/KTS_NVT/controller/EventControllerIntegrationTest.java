package Tim20.KTS_NVT.controller;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.sun.xml.fastinfoset.stax.EventLocation;

import tim20.KTS_NVT.dto.EventDTO;
import tim20.KTS_NVT.dto.EventDayDTO;
import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.dto.SectorPriceDTO;
import tim20.KTS_NVT.exceptions.SectorNotBelongLocationException;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.LocationService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.model.Error;

import javax.net.ssl.HttpsURLConnection;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EventService eventService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private SectorPriceService sectorPriceService;

	@Test
	public void getOneTest() {

		ResponseEntity<EventDTO> responseEntity = restTemplate.getForEntity("/events/1", EventDTO.class);

		EventDTO event = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(event);
		assertEquals("Event 1", event.getTitle());
	}

	@Test
	public void getOneNotFoundTest() {

		ResponseEntity<Error> responseEntity = restTemplate.getForEntity("/events/256", Error.class);

		Error error = responseEntity.getBody();

		Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		Assertions.assertEquals(1, error.getCode());
		Assertions.assertEquals("Event [256] not found", error.getMessage());

	}

	@Test
	public void getAllTest() {
		ResponseEntity<EventDTO[]> responseEntity = restTemplate.getForEntity("/events", EventDTO[].class);

		EventDTO[] events = responseEntity.getBody();

		Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		Assertions.assertEquals(4, events.length);

	}

	@Test
	public void addNewEventTest() {

		int size = eventService.findAll().size();

		EventDTO newDto = new EventDTO(null, "New event title", "New event descritpion");
		newDto.setActive(true);
		newDto.setMax_tickets(100);
		newDto.setEventCategory("SHOW");

		Location saved = locationService.saveLocation(new Location());

		newDto.setLocationID(saved.getId());

		ResponseEntity<EventDTO> responseEntity = restTemplate.postForEntity("/events", newDto, EventDTO.class);

		EventDTO dto = responseEntity.getBody();

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(dto);
		assertEquals(newDto.getTitle(), dto.getTitle());
		assertEquals(newDto.getDescription(), dto.getDescription());

		List<Event> events = eventService.findAll();
		assertEquals(size + 1, events.size());

		eventService.deleteEvent(dto.getId());
		locationService.deleteLocation(saved.getId());

	}

	@Test
	public void addNewEventTestWrongSector() {


		EventDTO newDto = new EventDTO(null, "New event title", "New event descritpion");
		newDto.setActive(true);
		newDto.setMax_tickets(100);
		newDto.setEventCategory("SHOW");

		Location saved = locationService.saveLocation(new Location());

		SectorDTO secDto = new SectorDTO();
		secDto.setId(101l);
		SectorPriceDTO spDto = new SectorPriceDTO();
		spDto.setSector(secDto);
		newDto.setPrices(new ArrayList<SectorPriceDTO>());
		newDto.getPrices().add(spDto);

		newDto.setLocationID(saved.getId());

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/events", newDto, Error.class);

		Error error = responseEntity.getBody();

		Assertions.assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
		Assertions.assertEquals(1, error.getCode());
		
		locationService.deleteLocation(saved.getId());

	}
	
	@Test
	public void addNewEventTestLocationNotAvailable() {


		EventDTO newDto = new EventDTO(null, "New event title", "New event descritpion");
		newDto.setActive(true);
		newDto.setMax_tickets(100);
		newDto.setEventCategory("SHOW");

		//Location location = locationService.findOne(2l);

		newDto.setLocationID(2l);
		
		newDto.setDays(new ArrayList<EventDayDTO>());
		EventDayDTO dayDto = new EventDayDTO();
		dayDto.setEventdate(Date.valueOf("2020-03-07"));
		
		newDto.getDays().add(dayDto);

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/events", newDto, Error.class);

		Error error = responseEntity.getBody();

		Assertions.assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		Assertions.assertEquals(1, error.getCode());
		

	}
	
	@Test
	public void addNewEventTestDatePast() {


		EventDTO newDto = new EventDTO(null, "New event title", "New event descritpion");
		newDto.setActive(true);
		newDto.setMax_tickets(100);
		newDto.setEventCategory("SHOW");

		//Location location = locationService.findOne(2l);

		newDto.setLocationID(2l);
		
		newDto.setDays(new ArrayList<EventDayDTO>());
		EventDayDTO dayDto = new EventDayDTO();
		dayDto.setEventdate(Date.valueOf("2019-03-07"));
		
		newDto.getDays().add(dayDto);

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/events", newDto, Error.class);

		Error error = responseEntity.getBody();

		Assertions.assertEquals(HttpStatus.EXPECTATION_FAILED, responseEntity.getStatusCode());
		Assertions.assertEquals(1, error.getCode());
		

	}

	@Test
	public void updateEventTest() {

		EventDTO dto = new EventDTO(1L, "Updated Title", "Updated Description");
		Location savedLocation = locationService.saveLocation(new Location());
		dto.setLocationID(savedLocation.getId());
		dto.setEventCategory("SHOW");
		ResponseEntity<EventDTO> responseEntity = restTemplate.exchange("/events", HttpMethod.PUT,
				new HttpEntity<EventDTO>(dto), EventDTO.class);

		EventDTO event = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(event);
		assertEquals(Long.valueOf(1L), event.getId());
		assertEquals("Updated Title", event.getTitle());
		assertEquals("Updated Description", event.getDescription());

		Event dbEvent = eventService.findOne(1L);
		assertEquals(Long.valueOf(1L), dbEvent.getId());
		assertEquals("Updated Title", dbEvent.getTitle());
		assertEquals("Updated Description", dbEvent.getDescription());

		// Vracanje podatka na staru vrednost
		dbEvent.setTitle("Event 1");
		dbEvent.setDescription("Event 1 Description");
		eventService.saveEvent(dbEvent);
		locationService.deleteLocation(savedLocation.getId());
	}

	@Test
	public void updateEventNotFoundTest() {
		ResponseEntity<Error> responseEntity = restTemplate.exchange("/events", HttpMethod.PUT,
				new HttpEntity<EventDTO>(new EventDTO(150L, "Updated Title", "Updated Description")), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Event [150] not found", error.getMessage());
	}

}
