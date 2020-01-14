package Tim20.KTS_NVT.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.exceptions.NotStanSectorException;
import tim20.KTS_NVT.exceptions.SectorNotFoundException;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventCategory;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.SeatsTicket;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.model.StandTicket;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.repository.EventRepository;
import tim20.KTS_NVT.repository.SectorRepository;
import tim20.KTS_NVT.repository.TicketRepository;
import tim20.KTS_NVT.service.TicketService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TicketServiceUnitTest {

	@Autowired
    private TicketService ticketService;
	
	@MockBean
    private TicketRepository ticketRepositoryMock;
	
	@MockBean
    private EventRepository eventRepositoryMock;
	
	@MockBean
    private SectorRepository sectorRepositoryMock;
	
	@Before
	public void setUp() {
		Long eventID = 1l;
	    EventDay day1 = new EventDay();
		Set<EventDay> eventDays = new HashSet();
		eventDays.add(day1);
		Boolean isActive = true;
		Integer maxTickets = 1000; 
		Location location = new Location();
		
		SectorPrice sp = new SectorPrice();
		Set<SectorPrice> prices = new HashSet();
		prices.add(sp);
		
		Set<Ticket> tickets = new HashSet();

	    Event e = new Event(eventID, "Exit", "Popular festival in Novi Sad", eventDays, isActive, EventCategory.MUSIC, maxTickets, location, prices, tickets);
		when(eventRepositoryMock.findById(eventID))
        .thenReturn(Optional.of(e));
		
		when(eventRepositoryMock.findById(2l))
        .thenReturn(Optional.empty());
		
		when(ticketRepositoryMock.findById(2l))
        .thenReturn(Optional.empty());
		
		when(sectorRepositoryMock.findById(3l))
        .thenReturn(Optional.empty());
		
		Sector sector = new SeatsSector();
		when(sectorRepositoryMock.findById(1l))
        .thenReturn(Optional.of(sector));
		
		Ticket t = new SeatsTicket(day1, true, (double) 5000, e, sector, 1, 1);

		when(ticketRepositoryMock.checkSeatsTicketAvailability(eventID, 1, 1, 1l))
        .thenReturn(t);
		
		when(ticketRepositoryMock.checkSeatsTicketSingleDayAvailability(eventID, 1, 1, 1l, 1l))
        .thenReturn(t);
		
		when(ticketRepositoryMock.checkSeatsTicketSingleDayAvailability(eventID, 1, 2, 1l, 1l))
        .thenReturn(null);
		
		when(ticketRepositoryMock.checkSeatsTicketAvailability(eventID, 1, 2, 1l))
        .thenReturn(null);
		
		when(sectorRepositoryMock.findById(2l))
        .thenReturn(null);
		
		Sector sector1 = new StandSector();
		Ticket t2 = new StandTicket(day1, true, (double) 5000, e, sector1);
		
		List<Ticket> myList = new ArrayList<Ticket>();
		myList.add(t);
		myList.add(t2);
		Mockito.when(ticketRepositoryMock.findAll()).thenReturn(myList);
	}
	
	
	@Test(expected = EventNotFoundException.class)
	public void addSeatTicket_notExistingEvent_throwsEventNotFoundException(){
		
		ticketService.addSeatTicket(2l, 1l, true, 5000, 1, 1, 1l);
	}
	
	@Test(expected = SectorNotFoundException.class)
	public void addSeatTicket_notExistingSector_throwsSectorNotFoundException(){
		
		ticketService.addSeatTicket(1l, 1l, true, 5000, 1, 1, 3l);
	}
	
	@Test
	public void addSeatTicketForAllDays_seatTaken_returnFalse(){
		
		boolean success = ticketService.addSeatTicket(1l, 1l, false, 5000, 1, 1, 1l);
		assertFalse(success);
	}
	
	@Test
	public void addSeatTicketForSingleDay_seatTaken_returnFalse(){
		
		boolean success = ticketService.addSeatTicket(1l, 1l, true, 5000, 1, 1, 1l);
		assertFalse(success);
	}
	
	@Test
	public void addSeatTicketForSingleDay_seatAvailable_returnTrue(){
		
		boolean success = ticketService.addSeatTicket(1l, 1l, true, 5000, 1, 2, 1l);
		assertTrue(success);
	}
	
	@Test
	public void addSeatTicketForAllDays_seatAvailable_returnTrue(){

		boolean success = ticketService.addSeatTicket(1l, 1l, false, 5000, 1, 2, 1l);
		assertTrue(success);
	}
	
	@Test(expected = EventNotFoundException.class)
	public void addStandTicket_notExistingEvent_throwsEventNotFoundException(){
		
		ticketService.addStandTicket(2l, 1l, true, 5000, 1l);
	}
	
	@Test(expected = SectorNotFoundException.class)
	public void addStandTicket_notExistingSector_throwsSectorNotFoundException(){
		
		ticketService.addStandTicket(1l, 1l, true, 5000, 3l);
	}
	
	@Test
	public void testFindAll(){
		assertTrue(ticketService.findAll().size() == 2);
	}
	
	@Test
	public void testFindOneEmpty(){
		Ticket ticket = ticketService.findOne(2l);
		assertNull(ticket);
	}
	
}
