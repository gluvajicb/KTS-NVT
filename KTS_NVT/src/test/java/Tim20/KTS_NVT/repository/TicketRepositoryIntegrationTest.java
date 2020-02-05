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

import tim20.KTS_NVT.model.SeatsTicket;
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
		SeatsTicket t = (SeatsTicket) ticketRepository.checkSeatsTicketAvailability(1l, 1, 1, 103l);
		
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
		SeatsTicket t = (SeatsTicket) ticketRepository.checkSeatsTicketSingleDayAvailability(1l, 1, 1, 103l, 1l);
		
		long eventId = t.getEvent().getId();
		long sectorId = t.getSector().getId();
		assertEquals(eventId, 1);
		assertEquals(sectorId, 103);
	}
	
	@Test
	public void checkNumberOfGuestsSingleDay() {
		int number = ticketRepository.checkNumberOfGuestsSingleDay(1l, 101l, 2l);
		
		assertEquals(3, number);
	}
	
	//zauzeta sedista
	
	@Test
	public void getSeatsTicketsByDayTest() {
		List<Ticket> tickets = ticketRepository.getSeatsTicketsByEventDay(2l);
		
		assertEquals(2, tickets.size());
		for (Ticket ticket : tickets) {
			assertEquals(2, ticket.getDay().getId().intValue());
		}
	}
	
	@Test
	public void getStandTicketsCountTest() {
		
		int count = ticketRepository.getStandTicketsCountByEventDayAndSector(2l, 102l);
		
		assertEquals(2, count);
		
	}

}
