package Tim20.KTS_NVT.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.service.EventService;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EventServiceIntegrationTest {

    @Autowired
    private EventService eventService;

    @Test
    public void findAllTest() {

        List<Event> events = eventService.findAll();

        assertNotNull(events);
        assertTrue(events.size() > 0);
    }


    @Test
    public void findOneTest() {

        Event event = eventService.findOne(1l);

        Assert.assertNotNull(event);
        assertEquals(1l, event.getId());
    }

    @Test
    public void findOneNotFoundTest() {

        Event event = eventService.findOne(1456l);

        assertNull(event);
    }

    @Test
    public void saveLocationTest() {

        Event event = new Event();
        event.setTitle("My new event");

        Event saved = eventService.saveEvent(event);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(event.getTitle(), saved.getTitle());

    }

    @Test
    public void saveEventWithId() {
        Event event = new Event();
        event.setId(357l);

        assertNull(eventService.saveEvent(event));

    }

    @Test
    public void updateEventTest() {

        Event event = new Event();
        event.setId(1l);
        event.setTitle("My new event");

        Event updated = eventService.updateEvent(event);

        assertNotNull(updated);
        assertEquals(event.getId(), updated.getId());
        assertEquals(event.getTitle(), updated.getTitle());

    }

    @Test
    public void updateEventWithoutId() {

        Event event = new Event();

        assertNull(eventService.updateEvent(event));

    }

    @Test
    public void deleteEvent() {
        eventService.deleteEvent(2l);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void deleteEventTestException() {
        eventService.deleteEvent(444l);
    }








}
