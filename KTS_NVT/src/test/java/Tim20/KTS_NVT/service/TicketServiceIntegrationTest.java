package Tim20.KTS_NVT.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.exceptions.EventDayNotFoundException;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.exceptions.SectorNotFoundException;
import tim20.KTS_NVT.model.StandTicket;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.repository.TicketRepository;
import tim20.KTS_NVT.service.TicketService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional

public class TicketServiceIntegrationTest {

	@Autowired
	private TicketService ticketService;

	@Autowired
	private TicketRepository ticketRepository;

	@Test
	public void findAllTest() {

		List<Ticket> tickets = ticketService.findAll();

		assertEquals(12, tickets.size());
	}

	@Test
	public void findOneTest() {
		Ticket ticket = ticketService.findOne(4l);
		assertNotNull(ticket);
	}

	@Test
	public void findOneTestNotFound() {
		Ticket ticket = ticketService.findOne(4589l);
		assertNull(ticket);
	}

	@Test
	public void saveTicketTest() {
		Ticket ticket = new StandTicket();

		int size = ticketRepository.findAll().size();

		Ticket saved = ticketService.saveTicket(ticket);

		assertEquals(size + 1, ticketRepository.findAll().size());
		assertNotNull(ticketRepository.findById(saved.getId()));
		ticketRepository.deleteById(saved.getId());
	}

	@Test
	public void addSeatsTicketTest() {
		int size = ticketRepository.findAll().size();

		boolean saved = ticketService.addSeatTicket(1l, 1l, true, 50, 3, 1, 101l, null);
		assertTrue(saved);
		
		int numTickets =  ticketRepository.findAll().size();
		assertEquals(size + 1,numTickets);
		// ticketRepository.deleteById(saved.getId());
	}

	@Test(expected = EventNotFoundException.class)
	public void addSeatsTicketTestEventNotFound() {

		ticketService.addSeatTicket(1485l, 1l, true, 50, 3, 1, 101l, null);

	}

	@Test(expected = SectorNotFoundException.class)
	public void addSeatsTicketTestSectorNotFound() {

		ticketService.addSeatTicket(1l, 1l, true, 50, 3, 1, 10155l, null);

	}
	
	@Test(expected = EventDayNotFoundException.class)
	public void addSeatsTicketTestDayNotFound() {

		ticketService.addSeatTicket(1l, 1585l, true, 50, 3, 1, 101l, null);

	}

	@Test
	public void addStandTicketTest() {
		int size = ticketRepository.findAll().size();

		boolean saved = ticketService.addStandTicket(1l, 1l, true, 20, 101l, null);
		assertTrue(saved);

		assertEquals(size + 1, ticketRepository.findAll().size());
		// ticketRepository.deleteById(saved.getId());
	}
	
	
	@Test(expected = SectorNotFoundException.class)
	public void addStandTicketTestSectorNotFound() {

		ticketService.addStandTicket(1l, 1l, true, 200, 5559l, null);

	}
	
	@Test(expected = EventDayNotFoundException.class)
	public void addStandTicketTestDayNotFound() {

		ticketService.addStandTicket(1l, 1554l, true, 500, 101l, null);

	}
	
	@Test(expected = EventNotFoundException.class)
	public void addStandTicketTestEventNotFound() {

		ticketService.addSeatTicket(1485l, 1l, true, 50, 3, 1, 101l, null);

	}

}
