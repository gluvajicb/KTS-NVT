package Tim20.KTS_NVT.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.repository.TicketRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TicketRepositoryIntegrationTest {
	@Autowired
	private TicketRepository ticketRepository;
	
	@Test
	public void checkSeatsTicketAvailability_Available() {
		Ticket t = ticketRepository.checkSeatsTicketAvailability(1l, 1, 2, 103l);
		
		assertNull(t);
	}
	
	@Test
	public void checkSeatsTicketAvailability_NotAvailable() {
		Ticket t = ticketRepository.checkSeatsTicketAvailability(1l, 1, 1, 103l);
		
		long eventId = t.getEvent().getId();
		long sectorId = t.getSector().getId();
		assertEquals(eventId, 1);
		assertEquals(sectorId, 103);
	}
	
	@Test
	public void checkSeatsTicketAvailabilitySingleDay_Available() {
		Ticket t = ticketRepository.checkSeatsTicketSingleDayAvailability(1l, 1, 2, 103l, 1l);
		
		assertNull(t);
	}
	
	@Test
	public void checkSeatsTicketAvailabilitySingleDay_NotAvailable() {
		Ticket t = ticketRepository.checkSeatsTicketSingleDayAvailability(1l, 1, 1, 103l, 1l);
		
		long eventId = t.getEvent().getId();
		long sectorId = t.getSector().getId();
		assertEquals(eventId, 1);
		assertEquals(sectorId, 103);
	}
	
	@Test
	public void checkNumberOfGuestsSingleDay() {
		int guests = ticketRepository.checkNumberOfGuestsSingleDay(1l, 101l, 1l);
		
		assertEquals(guests, 5);
	}

}
