package tim20.KTS_NVT.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim20.KTS_NVT.dto.EventDTO;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventCategory;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.LocationService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.service.TicketService;

import java.util.*;


@Controller
@RequestMapping(value = "/events")
public class EventController
{

    @Autowired
    private EventService eventService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private SectorPriceService sectorPriceService;

    @Autowired
    private TicketService ticketService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Event>> getAll() {

        Collection<Event> events = eventService.findAll();

        return new ResponseEntity<Collection<Event>>(events, HttpStatus.OK);

    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> addEvent(@RequestBody EventDTO dto)
    {
        Event event = new Event();
        event.setId(null);

        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        // event.setDates(dto.getDates());
        event.setIsActive(dto.getActive());

        /* ENUM - EVENT CATEGORY */

        if(dto.getEventCategory().equals(EventCategory.SHOW))
            event.setEventCategory(EventCategory.SHOW);
        else if (dto.getEventCategory().equals(EventCategory.SPORT))
            event.setEventCategory(EventCategory.SPORT);
        else if (dto.getEventCategory().equals(EventCategory.MUSIC))
            event.setEventCategory(EventCategory.MUSIC);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);


        /* LOCATION */
        event.setLocation(locationService.findOne(dto.getLocationID()));

        /* SECTOR PRICES */
        Set<SectorPrice> sectorprices = new HashSet<>();
        for(Long id : dto.getSectorpriceIDs())
        {
            SectorPrice sp = sectorPriceService.findOne(id);
            sectorprices.add(sp);
        }

        event.setSectorPrice(sectorprices);

        /* TICKETS */

        Set<Ticket> tickets = new HashSet<>();
        for(Long id : dto.getTicketIDs())
        {
            Ticket t = ticketService.findOne(id);
            tickets.add(t);
        }

        event.setTickets(tickets);

        try {
            event = eventService.saveEvent(event);
            return new ResponseEntity<>(event, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deactivateEvent(@PathVariable("id") Long id) {

        Event event = eventService.findOne(id);

        if (event == null) {
            throw new EventNotFoundException(id);
        } else {
            event.setIsActive(false);

            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    /* ************* SECTOR PRICE *************** */


    @GetMapping(value = "/sectorprices", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SectorPrice>> getAllSectorPrice() {

        Collection<SectorPrice> sectorprices = sectorPriceService.findAll();

        return new ResponseEntity<Collection<SectorPrice>>(sectorprices, HttpStatus.OK);

    }




    /* ----------    Exception Handler   ------------- */

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<Error> eventNotFound(EventNotFoundException e) {
        long eventId = e.getEventId();
        Error error = new Error(1, "Location [" + eventId + "] not found");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }


}
