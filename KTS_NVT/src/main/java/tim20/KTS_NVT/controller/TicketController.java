package tim20.KTS_NVT.controller;


import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tim20.KTS_NVT.converters.TicketDTOConverter;
import tim20.KTS_NVT.dto.SeatsTicketDTO;
import tim20.KTS_NVT.dto.StandTicketDTO;
import tim20.KTS_NVT.dto.TakenSeatsDTO;
import tim20.KTS_NVT.dto.TicketDTO;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.exceptions.MaxGuestsException;
import tim20.KTS_NVT.exceptions.NotStanSectorException;
import tim20.KTS_NVT.exceptions.SectorNotFoundException;
import tim20.KTS_NVT.exceptions.TakenSeatException;
import tim20.KTS_NVT.exceptions.TicketNotFoundException;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.model.User;
import tim20.KTS_NVT.repository.UserRepository;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.SectorService;
import tim20.KTS_NVT.service.TicketService;


@Controller
@CrossOrigin
@RequestMapping(value = "/tickets")
public class TicketController {


    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SectorService sectorService;
    
    @Autowired
    private UserRepository repository;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<TicketDTO>> getAll() {

        Set<Ticket> events = (Set<Ticket>)ticketService.findAll();
        Collection<TicketDTO> dtos = TicketDTOConverter.convertTicketsToDtos(events);

        
        return new ResponseEntity<Collection<TicketDTO>>(dtos, HttpStatus.OK);

    }


    @GetMapping(value = "/{eventId}")
    public ResponseEntity<Collection<TicketDTO>> getTicketsForEvent(@PathVariable("eventId") Long eventId) {

        Event event = eventService.findOne(eventId);

        if (event == null) {
            throw new EventNotFoundException(eventId);
        } else {
        	List<TicketDTO> dtos = TicketDTOConverter.convertTicketsToDtos(event.getTickets());
			return new ResponseEntity<Collection<TicketDTO>>(dtos, HttpStatus.OK);
        }

    }
    
    @GetMapping(value = "/user")
    public ResponseEntity<Collection<TicketDTO>> getTicketsForUser() {

    	String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
    	User user = repository.findByUsername(username);
    	

        if (user == null) {
			return new ResponseEntity<Collection<TicketDTO>>(HttpStatus.FORBIDDEN);

        } else {
        	List<TicketDTO> dtos = TicketDTOConverter.convertTicketsToDtos(ticketService.getTicketsForUser(user));
			return new ResponseEntity<Collection<TicketDTO>>(dtos, HttpStatus.OK);
        }

    }
    
    @PostMapping(value = "/add-seats-ticket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addSeatsTicket(@RequestBody SeatsTicketDTO dto) {
    	boolean success;
    	String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
    	System.out.println("++++++++++++++++++++++++++++++++++++");
    	System.out.println(username);
    	User user = repository.findByUsername(username);
    	try {

    		 success = ticketService.addSeatTicket(dto.getEventID(),dto.getEventDayID(), dto.isSingleDay(), dto.getPrice(), dto.getRowNumber(), dto.getColumnNumber(), dto.getSectorID(), user);
    	}catch(EventNotFoundException nf) {
    		throw new EventNotFoundException(dto.getEventID());
    	}catch(SectorNotFoundException nf){
    		throw new SectorNotFoundException(dto.getSectorID());
    	}
        
        if(success == false) {
        	return new ResponseEntity<Boolean>(success, HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<Boolean>(true, HttpStatus.OK);
    }
    
    @PostMapping(value = "/add-stand-ticket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addStandTicket(@RequestBody StandTicketDTO dto) {
    	String username = (String) SecurityContextHolder.getContext().getAuthentication().getName();
    	System.out.println("++++++++++++++++++++++++++++++++++++");
    	System.out.println(username);
    	

    	User user = repository.findByUsername(username);
    	boolean success;
    	
    	try {
    		success = ticketService.addStandTicket(dto.getEventID(),dto.getEventDayID(), dto.isSingleDay(), dto.getPrice(), dto.getSectorID(), user);
   	   	}catch(EventNotFoundException nf) {
   	   		throw new EventNotFoundException(dto.getEventID());
   	   	}catch(SectorNotFoundException nf){
   	   		throw new SectorNotFoundException(dto.getSectorID());
      	}
    	if(success == false) {
        	return new ResponseEntity<>(success, HttpStatus.BAD_REQUEST);
        }
        
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable("id") Long id) {

        Ticket ticket = ticketService.findOne(id);

        if (ticket == null) {
            throw new TicketNotFoundException(id);
        } else {
            Event event = eventService.findOne(ticket.getEvent().getId());
            event.getTickets().remove(ticket);
            eventService.updateEvent(event);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    // zauzeta sedista
    
    @GetMapping(value = "/takenSeats/{dayId}")
    public ResponseEntity<TakenSeatsDTO> getTakenSeats(@PathVariable("dayId") Long dayId) {
    	TakenSeatsDTO dto = ticketService.getTakenSeats(dayId);
    	return new ResponseEntity<TakenSeatsDTO>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/takenSeatsAllDays/{eventId}")
    public ResponseEntity<TakenSeatsDTO> getTakenSeatsAllDays(@PathVariable("eventId") Long eventId) {
    	TakenSeatsDTO dto = ticketService.getTakenSeatsAllDays(eventId);
    	return new ResponseEntity<TakenSeatsDTO>(dto, HttpStatus.OK);
    }

    /* ----------    Exception Handler   ------------- */

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Error> eventNotFound(EventNotFoundException e) {
        long eventId = e.getEventId();
        Error error = new Error(1, "Event [" + eventId + "] not found");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Error> ticketNotFound(TicketNotFoundException e) {
        long eventId = e.getTicketId();
        Error error = new Error(1, "Ticket [" + eventId + "] not found");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(TakenSeatException.class)
    public ResponseEntity<Error> takenSeatTicket(TakenSeatException e)
    {
        int row = e.getRowNum();
        int col = e.getColNum();
        Error error = new Error(1, "Seat [ row: " + row + ", column: " + col + "] is already reserved.");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotStanSectorException.class)
    public ResponseEntity<Error> notStandSector(NotStanSectorException e)
    {
        Error error = new Error(1,"Sector is not STAND type.");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxGuestsException.class)
    public ResponseEntity<Error> maxGuestExc(MaxGuestsException e)
    {
        Error error = new Error(1, "There is max number of guests in sector.");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(SectorNotFoundException.class)
	public ResponseEntity<Error> sectorNotFound(SectorNotFoundException e) {
		long locationId = e.getSectorId();
		Error error = new Error(1, "Sector [" + locationId + "] not found");
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}


}
