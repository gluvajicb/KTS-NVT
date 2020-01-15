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
import tim20.KTS_NVT.dto.EventDTO;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.model.Error;

import javax.net.ssl.HttpsURLConnection;

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

        ResponseEntity<EventDTO> responseEntity = restTemplate.postForEntity("/events",
                new EventDTO(null, "New Event Title", "New Description"), EventDTO.class);

        EventDTO dto = responseEntity.getBody();

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(dto);
        assertEquals("New Event Title", dto.getTitle());
        assertEquals("New Description", dto.getDescription());

        List<Event> events = eventService.findAll();
        assertEquals(size + 1, events.size());

        eventService.deleteEvent(dto.getId());

    }

    @Test
    public void updateEventTest() {
        ResponseEntity<EventDTO> responseEntity =
                restTemplate.exchange("/events", HttpMethod.PUT,
                        new HttpEntity<EventDTO>(new EventDTO(1L, "Updated Title", "Updated Description")),
                        EventDTO.class);

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
    }

    @Test
    public void updateEventNotFoundTest() {
        ResponseEntity<Error> responseEntity = restTemplate.exchange("/events", HttpMethod.PUT,
                new HttpEntity<EventDTO>(new EventDTO(150L, "Updated Title", "Updated Description")),
                Error.class);

        Error error = responseEntity.getBody();

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(1, error.getCode());
        assertEquals("Event [150] not found", error.getMessage());
    }









































}
