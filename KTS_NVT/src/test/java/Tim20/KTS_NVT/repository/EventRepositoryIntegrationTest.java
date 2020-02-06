package Tim20.KTS_NVT.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.repository.EventRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EventRepositoryIntegrationTest {

	@Autowired
	private EventRepository eventRepository;

	@Test
	public void getUpcomingEventsTest() {

		List<Event> events = eventRepository.getUpcomingEvents();

		assertNotNull(events);
		assertEquals(2, events.size());

		boolean hasUpcomingDate;
		for (Event event : events) {
			hasUpcomingDate = false;

			for (EventDay day : event.getEventDays()) {
				if (day.getEventDate().after(new Date())) {
					hasUpcomingDate = true;
					break;
				}
				
				assertTrue(hasUpcomingDate);
			}
		}
	}
}
