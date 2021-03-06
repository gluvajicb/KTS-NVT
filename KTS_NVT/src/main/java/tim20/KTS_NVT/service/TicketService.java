package tim20.KTS_NVT.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tim20.KTS_NVT.dto.TakenSeatsDTO;
import tim20.KTS_NVT.dto.TakenSeatsDTO.Seat;
import tim20.KTS_NVT.dto.TakenSeatsDTO.Seats;
import tim20.KTS_NVT.dto.TakenSeatsDTO.Stand;
import tim20.KTS_NVT.exceptions.EventDayNotFoundException;
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
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.repository.TicketRepository;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private EventDayService dayService;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private EmailService emailService;

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

    public Set<Ticket> getTicketsForUser(User user){
    	return ticketRepository.findByUserId(user.getId());
    }

    public Ticket saveTicket(Ticket ticket) {

        Ticket t = (Ticket)ticketRepository.save(ticket);
        return t;
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addSeatTicket(Long eventID, Long eventDayID, boolean isSingleDay, double price, int rowNumber, int columnNumber, Long sectorID, User user) {
    	Event event = eventService.findOne(eventID);

        if (event == null) {
            throw new EventNotFoundException(eventID);
        }

        Sector s = sectorService.findOne(sectorID);
        if (s == null) {
            throw new SectorNotFoundException(sectorID);
        }
        
        EventDay day = dayService.findOne(eventDayID);
        
        if(day == null) {
        	throw new EventDayNotFoundException(eventDayID);
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
        t.setDay(day);
        t.setPrice(price);
        t.setSingleDay(isSingleDay);
        t.setColumnNum(columnNumber);
        t.setRowNum(rowNumber);
        t.setUser(user);
        t.setPaid(false);
        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
        
        t.setReservationDate(date);

        t.setSector(s);

        event.getTickets().add(t);
        eventService.updateEvent(event);

        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        emailService.sendNewOrderEmail(t, u.getEmail());

        return true;
        
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public boolean addStandTicket(Long eventID, Long eventDayID, boolean isSingleDay, double price, Long sectorID, User user) {
    	Event event = eventService.findOne(eventID);

        if (event == null) {
            throw new EventNotFoundException(eventID);
        }
        
        EventDay day = dayService.findOne(eventDayID);
        
        if(day == null) {
        	throw new EventDayNotFoundException(eventDayID);
        }
        
        StandSector s = (StandSector) sectorService.findOne(sectorID);
        if (s == null) {
            throw new SectorNotFoundException(sectorID);
        }

        int guestNum = ticketRepository.checkNumberOfGuests(eventID, sectorID, eventDayID);
        

        if(guestNum >= s.getMaxGuests()) {
            return false;
        }

        StandTicket t = new StandTicket();
        t.setEvent(event);
        t.setDay(day);
        t.setPrice(price);
        t.setSingleDay(isSingleDay);
        t.setSector(s);
        t.setUser(user);
        t.setPaid(false);

        long millis=System.currentTimeMillis();  
        java.sql.Date date=new java.sql.Date(millis);  
        
        t.setReservationDate(date);
        event.getTickets().add(t);
        eventService.updateEvent(event);

        User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        emailService.sendNewOrderEmail(t, u.getEmail());

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
    

    public TakenSeatsDTO getTakenSeatsAllDays(Long eventId) {
    	TakenSeatsDTO returnTaken = new  TakenSeatsDTO();
    	Event event = eventService.findOne(eventId);

        if (event == null) {
            throw new EventNotFoundException(eventId);
        }
        
        TakenSeatsDTO takenSeatsDay;
        for(EventDay day : event.getEventDays()) {
        	takenSeatsDay = this.getTakenSeats(day.getId());
        	
        	for(Stand standSector : takenSeatsDay.getStandTaken()) {
        		boolean found = false;
        		for(Stand stand : returnTaken.getStandTaken()) {
        			if(stand.getSectorId() == standSector.getSectorId()) {
        				if(stand.getCount() < standSector.getCount())
        					stand.setCount(standSector.getCount());
        				
        				found = true;
        				break;
        			}
        			
        		}
        		
        		if(!found)
    				returnTaken.getStandTaken().add(standSector);
    			}
        	
	        for(Seats standSector : takenSeatsDay.getSeatsTaken()) {
	    		boolean found = false;
	    		for(Seats stand : returnTaken.getSeatsTaken()) {
	    			if(stand.getSectorId() == standSector.getSectorId()) {
	    				
	    				for(Seat seat : standSector.getSeats()) {
		    				if(!stand.getSeats().contains(seat))
		    					stand.getSeats().add(seat);
	    				}
	    				found = true;
	    				break;
	    			}
	    			
	    		}
	    		
	    		if(!found)
					returnTaken.getSeatsTaken().add(standSector);
				}
	    	}
        
        return returnTaken;
    }
	public TakenSeatsDTO getTakenSeats(Long dayId) {
	    	
    	TakenSeatsDTO soldedTicketsBySectors = new TakenSeatsDTO();
    	// Key id sektora, value lista [row, column]
    	Map<Long, List<List<Integer>>> takenSeats = new HashMap<Long, List<List<Integer>>>();
    	EventDay day = dayService.findOne(dayId);
    	
    	if(day == null)
    		throw new EventDayNotFoundException(dayId);
    	
    	List<Ticket> data = ticketRepository.getSeatsTicketsByEventDay(dayId, day.getEvent().getId());
    
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
    	    	
    	for (SectorPrice sp: day.getEvent().getSectorPrice()) {
    		Sector s = sp.getSector();
    		if (s instanceof StandSector) {
    			int count = ticketRepository.getStandTicketsCountByEventDayAndSector(dayId, s.getId(), day.getEvent().getId());
    			soldedTicketsBySectors.getStandTaken().add(new Stand(s.getId(), count));
    		}
    	}
    	
    	return soldedTicketsBySectors;
    }
}
