package tim20.KTS_NVT.controller;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tim20.KTS_NVT.converters.EventDTOConverter;
import tim20.KTS_NVT.converters.SectorPriceDTOConverter;
import tim20.KTS_NVT.dto.EventDTO;
import tim20.KTS_NVT.dto.SectorPriceDTO;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.exceptions.IdNotFoundException;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.service.EventDayService;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.LocationService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.service.TicketService;

@Controller
@RequestMapping(value = "/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private SectorPriceService sectorPriceService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private EventDayService eventDayServise;

	@GetMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> getOne(@PathVariable("eventId") Long id) {

		Event event = eventService.findOne(id);

		if(event == null) {
			throw new EventNotFoundException(id);
		} else {
			EventDTO dto = EventDTOConverter.eventToDto(event);
			return new ResponseEntity<EventDTO>(dto, HttpStatus.OK);
		}
	}

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EventDTO>> getAll() {

		Collection<Event> events = eventService.findAll();

		List<EventDTO> dtos = EventDTOConverter.eventsToDtos(events);

		return new ResponseEntity<List<EventDTO>>(dtos, HttpStatus.OK);

	}

	@GetMapping(value = "/{eventId}/days")
	public ResponseEntity<Collection<EventDay>> getAllDaysForEvent(@PathVariable("eventId") Long eventId) {

		Event event = eventService.findOne(eventId);

		if (event == null) {
			throw new EventNotFoundException(eventId);
		} else {
			return new ResponseEntity<Collection<EventDay>>(event.getEventDays(), HttpStatus.OK);
		}

	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> addEvent(@RequestBody EventDTO dto) {

		Event event = EventDTOConverter.dtoToEvent(dto);
		event.setId(null);
		Event e = eventService.saveEvent(event);

		return new ResponseEntity<EventDTO>(EventDTOConverter.eventToDto(e), HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO dto) {

		if(dto.getId() == null) {
			throw new IdNotFoundException();
		}

		Event found = eventService.findOne(dto.getId());

		if(found == null) {
			throw new EventNotFoundException(dto.getId());
		}

		Event event = EventDTOConverter.dtoToEvent(dto);

		Event e = eventService.updateEvent(event);

		return new ResponseEntity<EventDTO>(EventDTOConverter.eventToDto(e), HttpStatus.OK);
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
	public ResponseEntity<List<SectorPriceDTO>> getAllSectorPrice() {

		Collection<SectorPrice> sectorprices = sectorPriceService.findAll();

		List<SectorPriceDTO> dtos = SectorPriceDTOConverter.sectorpricesToDtos(sectorprices);

		return new ResponseEntity<List<SectorPriceDTO>>(dtos, HttpStatus.OK);

	}

	@GetMapping(value = "{eventId}/sectorprices", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<SectorPriceDTO>> getSectorPriceForEvent(@PathVariable("eventId") Long eventId) {

		Event event = eventService.findOne(eventId);

		if(event == null) {
			throw new EventNotFoundException(eventId);
		} else {
			List<SectorPriceDTO> dtos = SectorPriceDTOConverter.sectorpricesToDtos(event.getSectorPrice());
			return new ResponseEntity<Collection<SectorPriceDTO>>(dtos, HttpStatus.OK);
		}

	}

	/* ---------- Exception Handler ------------- */

	@ExceptionHandler(EventNotFoundException.class)
	public ResponseEntity<Error> eventNotFound(EventNotFoundException e) {
		long eventId = e.getEventId();
		Error error = new Error(1, "Location [" + eventId + "] not found");
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}

}
