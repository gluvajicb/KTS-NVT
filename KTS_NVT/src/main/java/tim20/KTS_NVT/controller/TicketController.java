package tim20.KTS_NVT.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import tim20.KTS_NVT.converters.SectorDTOConverter;
import tim20.KTS_NVT.converters.TicketDTOConverter;
import tim20.KTS_NVT.dto.SeatsTicketDTO;
import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.dto.StandTicketDTO;
import tim20.KTS_NVT.dto.TicketDTO;
import tim20.KTS_NVT.exceptions.*;
import tim20.KTS_NVT.model.*;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.SectorService;
import tim20.KTS_NVT.service.TicketService;

import java.util.*;


@Controller
@RequestMapping(value = "/tickets")
public class TicketController {


    @Autowired
    private EventService eventService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private SectorService sectorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Ticket>> getAll() {

        Collection<Ticket> events = ticketService.findAll();

        return new ResponseEntity<Collection<Ticket>>(events, HttpStatus.OK);

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
    
    @PostMapping(value = "/add-seats-ticket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addSeatsTicket(@RequestBody SeatsTicketDTO dto) {
    	boolean success;
    	try {
    		 success = ticketService.addSeatTicket(dto.getEventID(),dto.getEventDayID(), dto.isSingleDay(), dto.getPrice(), dto.getRowNumber(), dto.getColumnNumber(), dto.getSectorID());
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
    
    @PostMapping(value = "/add-stand-ticket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> addStandTicket(@RequestBody StandTicketDTO dto) {
    	boolean success = ticketService.addStandTicket(dto.getEventID(),dto.getEventDayID(), dto.isSingleDay(), dto.getPrice(), dto.getSectorID());
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

            ticketService.deleteTicket(ticket.getId());

            return new ResponseEntity<Void>(HttpStatus.OK);
        }
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
