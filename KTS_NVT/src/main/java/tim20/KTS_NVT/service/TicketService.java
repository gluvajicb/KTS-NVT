package tim20.KTS_NVT.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim20.KTS_NVT.dto.TakenSeatsDTO;
import tim20.KTS_NVT.dto.TakenSeatsDTO.Seat;
import tim20.KTS_NVT.dto.TakenSeatsDTO.Seats;
import tim20.KTS_NVT.dto.TakenSeatsDTO.Stand;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.exceptions.SectorNotFoundException;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.model.SeatsTicket;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.model.StandTicket;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.repository.DayRepository;
import tim20.KTS_NVT.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private SectorService sectorService;

    public List<Ticket> findAll() {

        List<Ticket> tickets = ticketRepository.findAll();

        return tickets;
    }


    public Ticket findOne(Long id) {

        Optional<Ticket> ticket = ticketRepository.findById(id);

        if(ticket.isPresent()) {
            return ticket.get();
        }

        return null;
    }


    public Ticket saveTicket(Ticket ticket) {

        Ticket t = (Ticket)ticketRepository.save(ticket);
        return t;
    }
    
    public boolean addSeatTicket(Long eventID, Long eventDayID, boolean isSingleDay, double price, int rowNumber, int columnNumber, Long sectorID) {
    	Event event = eventService.findOne(eventID);

        if (event == null) {
            throw new EventNotFoundException(eventID);
        }

        Sector s = sectorService.findOne(sectorID);
        if (s == null) {
            throw new SectorNotFoundException(sectorID);
        }
        
        boolean isAvailable;
        if (!isSingleDay) {
        	isAvailable = checkSeatsTicketAvailability(eventID, rowNumber, columnNumber, sectorID);
        } else {

        	isAvailable = checkSeatsTicketSingleDayAvailability(eventID, rowNumber, columnNumber, sectorID, eventDayID);
        }

        if(!isAvailable) {
            return false;
        }

        SeatsTicket t = new SeatsTicket();
        t.setEvent(event);
        t.setPrice(price);
        t.setSingleDay(isSingleDay);
        t.setColumnNum(columnNumber);
        t.setRowNum(rowNumber);
        
        t.setSector(s);

        event.getTickets().add(t);
        eventService.updateEvent(event);
        
        saveTicket(t);
        return true;
        
    }
    
    public boolean addStandTicket(Long eventID, Long eventDayID, boolean isSingleDay, double price, Long sectorID) {
    	Event event = eventService.findOne(eventID);

        if (event == null) {
            throw new EventNotFoundException(eventID);
        }
        
        Sector s = sectorService.findOne(sectorID);
        if (s == null) {
            throw new SectorNotFoundException(sectorID);
        }

        int guestNum = ticketRepository.checkNumberOfGuests(eventID, sectorID, eventDayID);
        

        if(guestNum >= 1000) {
            return false;
        }

        StandTicket t = new StandTicket();
        t.setEvent(event);
        t.setPrice(price);
        t.setSingleDay(isSingleDay);
        t.setSector(s);

        event.getTickets().add(t);
        eventService.updateEvent(event);

        saveTicket(t);
        return true;
    }

    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }
    
    public boolean checkSeatsTicketAvailability(Long eventId, Integer rowNum, Integer colNum, Long sectorId) {
		
		Ticket t = ticketRepository.checkSeatsTicketAvailability(eventId, rowNum, colNum, sectorId);
		
		if(t == null) {
			return true;
		}else {
			return false;
		}
		
	}
    
    public boolean checkSeatsTicketSingleDayAvailability(Long eventId, Integer rowNum, Integer colNum, Long sectorId, Long dayId) {
		
		Ticket t = ticketRepository.checkSeatsTicketSingleDayAvailability(eventId, rowNum, colNum, sectorId, dayId);
		
		if(t == null) {
			return true;
		}else {
			return false;
		}
		
	}

public TakenSeatsDTO getTakenSeats(Long dayId) {
    	
    	TakenSeatsDTO soldedTicketsBySectors = new TakenSeatsDTO();
    	// Key id sektora, value lista [row, column]
    	Map<Long, List<List<Integer>>> takenSeats = new HashMap<Long, List<List<Integer>>>();
    	
    	List<Ticket> data = ticketRepository.getSeatsTicketsByEventDay(dayId);
    
    	for (Ticket ticket : data) {
			if (!takenSeats.containsKey(ticket.getSector().getId())) {
				List<List<Integer>> list = new ArrayList<List<Integer>>();
				takenSeats.put(ticket.getSector().getId(), list);
			}
			
			List<Integer> rowColumn = new ArrayList<Integer>();
			rowColumn.add(((SeatsTicket)ticket).getRowNum());
			rowColumn.add(((SeatsTicket)ticket).getColumnNum());
			
			takenSeats.get(ticket.getSector().getId()).add(rowColumn);
			
		}
    	
    	
    	
    	for (Long sId : takenSeats.keySet()) {
    		Seats forSeats = new TakenSeatsDTO.Seats();
    		forSeats.setSectorId(sId);
    		
    		for (List<Integer> i : takenSeats.get(sId)) {
				forSeats.addSeat(new Seat(i.get(0), i.get(1)));
			}
    		
    		soldedTicketsBySectors.getSeatsTaken().add(forSeats);
		}
    	
    
    	// za stand
    	// key - id sektora, value broj karata
    	//Map<Long, Integer> soldTickets= new HashMap<Long, Integer>();
    	
    	EventDay day = dayRepository.getOne(dayId);
    	
    	for (SectorPrice sp: day.getEvent().getSectorPrice()) {
    		Sector s = sp.getSector();
    		if (s instanceof StandSector) {
    			int count = ticketRepository.getStandTicketsCountByEventDayAndSector(dayId, s.getId());
    			soldedTicketsBySectors.getStandTaken().add(new Stand(s.getId(), count));
    		}
    	}
    	
    	return soldedTicketsBySectors;
    }
}
