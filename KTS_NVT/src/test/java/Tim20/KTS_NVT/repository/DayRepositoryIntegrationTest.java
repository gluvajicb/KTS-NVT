package Tim20.KTS_NVT.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.repository.DayRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class DayRepositoryIntegrationTest {

	@Autowired
	private DayRepository dayRepository;
	
	
	@Test
	public void checkAvaibilityTestAvailable() {
		
		EventDay day = dayRepository.checkAvailability(Date.valueOf("2020-05-10"), new Long(1l));
	
		assertNull(day);
	}
	
	@Test
	public void checkAvaibilityTestNotAvailable() {
		
		EventDay day = dayRepository.checkAvailability(Date.valueOf("2020-03-06"), new Long(2l));
	
		assertNotNull(day);
	}
	
	@Test
	public void checkAvaibilityTestAvailable2() {
		
		EventDay day = dayRepository.checkAvailability(Date.valueOf("2020-03-06"), new Long(1l));
	
		assertNull(day);
	}
	
}
