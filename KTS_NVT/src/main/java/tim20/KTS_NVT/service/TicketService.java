package tim20.KTS_NVT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import tim20.KTS_NVT.dto.SeatsTicketDTO;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.exceptions.MaxGuestsException;
import tim20.KTS_NVT.exceptions.NotStanSectorException;
import tim20.KTS_NVT.exceptions.SectorNotFoundException;
import tim20.KTS_NVT.exceptions.TakenSeatException;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.model.SeatsTicket;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.model.StandTicket;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.repository.TicketRepository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;
    
    @Autowired
    private EventService eventService;

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

}
