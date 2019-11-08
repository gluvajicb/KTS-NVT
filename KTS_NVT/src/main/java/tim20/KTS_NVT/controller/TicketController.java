package tim20.KTS_NVT.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim20.KTS_NVT.dto.SeatsTicketDTO;
import tim20.KTS_NVT.dto.StandTicketDTO;
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
    public ResponseEntity<Collection<Ticket>> getTicketsForEvent(@PathVariable("eventId") Long eventId) {

        Event event = eventService.findOne(eventId);

        if (event == null) {
            throw new EventNotFoundException(eventId);
        } else {
            return new ResponseEntity<Collection<Ticket>>(event.getTickets(), HttpStatus.OK);
        }

    }

    @PostMapping(value = "/add-seats-ticket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> addSeatsTicket(@RequestBody SeatsTicketDTO dto) {
        Event event = eventService.findOne(dto.getEventID());

        if (event == null) {
            throw new EventNotFoundException(dto.getEventID());
        }

        boolean isBooked = false;
        for (Ticket t : event.getTickets()) {
            if (t instanceof SeatsTicket) {
                if (!dto.isSingleDay()) {
                    /*Ako je za sve dane, ne gledamo koji datum je zauzet, odmah prekidamo petlju*/
                    if (((SeatsTicket) t).getColumnNumber() == dto.getColumnNumber() && ((SeatsTicket) t).getRowNumber() == dto.getRowNumber()) {
                        if(t.getSector().getId() == dto.getSectorID()) {
                            isBooked = true;
                            break;
                        }
                    }
                } else {

                    /*Ako jeste single day, moramo bitno nam je i kog dana je zauzeto*/

                    if (t.getDay().getId() == dto.getEventDayID()) {
                        if (((SeatsTicket) t).getColumnNumber() == dto.getColumnNumber() && ((SeatsTicket) t).getRowNumber() == dto.getRowNumber()) {
                            if(t.getSector().getId() == dto.getSectorID()) {
                                isBooked = true;
                                break;
                            }
                        }
                    }
                }
            }
        }

        if(isBooked) {
            throw  new TakenSeatException(dto.getRowNumber(), dto.getColumnNumber());
        }

        SeatsTicket t = new SeatsTicket();
        t.setEvent(event);
        t.setPrice(dto.getPrice());
        t.setSingleDay(dto.isSingleDay());
        t.setColumnNumber(dto.getColumnNumber());
        t.setRowNumber(dto.getRowNumber());
        Sector s = sectorService.findOne(dto.getSectorID());
        t.setSector(s);

        event.getTickets().add(t);
        eventService.updateEvent(event);

        try {
            Ticket t1 = ticketService.saveTicket(t);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/add-stand-ticket", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> addStandTicket(@RequestBody StandTicketDTO dto) {
        Event event = eventService.findOne(dto.getEventID());

        if (event == null) {
            throw new EventNotFoundException(dto.getEventID());
        }

        int guestNum = 0;
        for (Ticket t : event.getTickets()) {
            if (t instanceof StandTicket) {
                if(t.getSector().getId() == dto.getSectorID()) {
                    guestNum++;
                }
            }
        }

        StandSector s;
        try {
            s = (StandSector) sectorService.findOne(dto.getSectorID());
        }catch (Exception e){
            throw new NotStanSectorException();
        }

        if(guestNum >= s.getMaxGuests()) {
            throw  new MaxGuestsException();
        }

        StandTicket t = new StandTicket();
        t.setEvent(event);
        t.setPrice(dto.getPrice());
        t.setSingleDay(dto.isSingleDay());
        t.setSector(s);

        event.getTickets().add(t);
        eventService.updateEvent(event);

        try {
            Ticket t1 = ticketService.saveTicket(t);
            return new ResponseEntity<>(t, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
        Error error = new Error(1, "Location [" + eventId + "] not found");
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


}
