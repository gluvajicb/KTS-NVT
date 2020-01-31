package tim20.KTS_NVT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim20.KTS_NVT.converters.EventDayDTOConverter;
import tim20.KTS_NVT.dto.EventDayDTO;
import tim20.KTS_NVT.exceptions.DateInThePastException;
import tim20.KTS_NVT.exceptions.EventDayNotFoundException;
import tim20.KTS_NVT.exceptions.IdNotFoundException;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.service.EventDayService;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "eventdays")
public class EventDayController {

    @Autowired
    private EventDayService eventDayService;


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EventDayDTO>> getAll() {

        Collection<EventDay> eventday = eventDayService.findAll();

        List<EventDayDTO> dtos = EventDayDTOConverter.eventDaysToDtos(eventday);

        return new ResponseEntity<List<EventDayDTO>>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDayDTO> getOne(@PathVariable("dayId") Long id) {

        EventDay eventDay = eventDayService.findOne(id);

        if (eventDay == null) {
            throw new EventDayNotFoundException(id);
        } else {
            EventDayDTO dto = EventDayDTOConverter.eventDayToDto(eventDay);
            return new ResponseEntity<EventDayDTO>(dto, HttpStatus.OK);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDayDTO> addEventDay(@RequestBody EventDayDTO dto) {

        EventDay eventday = EventDayDTOConverter.dtoToEventDay(dto);
        eventday.setId(null);

        Date today = Calendar.getInstance().getTime();

        if (dto.getEventdate().before(today)) {
            throw new DateInThePastException();
        }

        EventDay ed = eventDayService.saveEventDay(eventday);

        return new ResponseEntity<EventDayDTO>(EventDayDTOConverter.eventDayToDto(ed), HttpStatus.CREATED);

    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EventDayDTO> updateEventDay(@RequestBody EventDayDTO dto) {

        if(dto.getId() == null) {
            throw new IdNotFoundException();
        }

        EventDay found = eventDayService.findOne(dto.getId());

        if(found == null) {
            throw new EventDayNotFoundException(dto.getId());
        }

        EventDay eventDay = EventDayDTOConverter.dtoToEventDay(dto);

        EventDay ed = eventDayService.updateEventDay(eventDay);

        return new ResponseEntity<EventDayDTO>(EventDayDTOConverter.eventDayToDto(ed), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{dayId}")
    public ResponseEntity<Void> deleteEventDay(@PathVariable("dayId") Long id) {

        EventDay eventDay = eventDayService.findOne(id);

        if(eventDay == null) {
            throw new EventDayNotFoundException(id);
        } else {
            eventDayService.deleteEventDay(id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }
    }

    // ************************ EXCEPTION HANDLER ************************ //

    @ExceptionHandler(EventDayNotFoundException.class)
    public ResponseEntity<Error> eventDayNotFound(EventDayNotFoundException e) {
        long dayId = e.getEventDayId();
        Error error = new Error(1, "EventDay [" + dayId + "] not found");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }

}
