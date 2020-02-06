package Tim20.KTS_NVT.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.repository.DayRepository;
import tim20.KTS_NVT.service.EventDayService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class EventDayServiceIntegrationTest {

	@Autowired
	private EventDayService dayService;

	@Autowired
	private DayRepository dayRepository;
	
	@Test
	public void findAllTest() {
		List<EventDay> days = dayService.findAll();
		
		assertNotNull(days);
		assertEquals(4, days.size());
	}
	
	@Test
	public void findOneTest() {

		EventDay day = dayService.findOne(2l);

		assertNotNull(day);
		assertEquals(2, day.getId());
	}

	@Test
	public void findOneNotFoundTest() {

		EventDay day = dayService.findOne(24855l);
		assertNull(day);
	}
	
	@Test
	public void saveDayTest() {

		EventDay day = new EventDay();
		day.setTitle("My new EVENT DAY");

		EventDay saved = dayService.saveEventDay(day);

		assertNotNull(saved);
		assertNotNull(saved.getId());
		assertEquals(day.getTitle(), saved.getTitle());
		
		assertNotNull(dayRepository.findById(saved.getId()));
		
		dayRepository.deleteById(saved.getId());
	}

	@Test
	public void saveDayWithId() {
		EventDay day = new EventDay();
		day.setId(352l);

		assertNull(dayService.saveEventDay(day));

	}
	
	@Test
	public void deleteDayTest() {
		EventDay saved = dayRepository.save(new EventDay());
		int size = dayRepository.findAll().size();
		dayService.deleteEventDay(saved.getId());
		
		assertEquals(size - 1, dayRepository.findAll().size());
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void deleteLocationTestException() {
		dayService.deleteEventDay(444l);
	}
	
	@Test
	public void checkAvailabilityTestAvailable() {
		
		assertTrue(dayService.checkAvailability(Date.valueOf("2020-03-06"), new Long(1l)));
	}
	
	@Test
	public void checkAvailabilityTestNotAvailable() {
		
		assertFalse(dayService.checkAvailability(Date.valueOf("2020-03-06"), new Long(2l)));
	}
}
