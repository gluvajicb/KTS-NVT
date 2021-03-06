package tim20.KTS_NVT.controller;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import tim20.KTS_NVT.exceptions.DateInThePastException;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.exceptions.IdNotFoundException;
import tim20.KTS_NVT.exceptions.LocationNotAvailableException;
import tim20.KTS_NVT.exceptions.SectorNotBelongLocationException;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.service.EventDayService;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.LocationService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.service.SectorService;
import tim20.KTS_NVT.service.TicketService;

@Controller
@CrossOrigin
@RequestMapping(value = "/events")
public class EventController {

	@Autowired
	private EventService eventService;
	
	@Autowired
	private EventDayService dayService;

	@Autowired
	private LocationService locationService;

	@Autowired
	private SectorPriceService sectorPriceService;

	@Autowired
	private SectorService sectorService;

	@Autowired
	private TicketService ticketService;

	@Autowired
	private EventDayService eventDayServise;

	@GetMapping(value = "/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> getOne(@PathVariable("eventId") Long id) {

		Event event = eventService.findOne(id);

		if (event == null) {
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

	@GetMapping(value = "/upcoming")
	public ResponseEntity<List<EventDTO>> getUpcomingEvents() {

		Collection<Event> events = eventService.getUpcomingEvents();

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

		Location location = locationService.findOne(dto.getLocationID());
		event.setLocation(location);

		event.setSectorPrice(new HashSet<SectorPrice>());
		if (dto.getPrices() != null) {
			for (SectorPriceDTO sd : dto.getPrices()) {

				boolean found = false;

				for (Sector sector : location.getSectors()) {
					if (sector.getId() == sd.getSector().getId()) {
						found = true;
						break;
					}
				}

				if (!found) {
					throw new SectorNotBelongLocationException(sd.getSector().getId(), location.getId(), 0l);
				}

				SectorPrice newSp = new SectorPrice();
				newSp.setPrice(sd.getPrice());
				newSp.setSector(sectorService.findOne(sd.getSector().getId()));
				event.getSectorPrice().add(newSp);
				newSp.setEvent(event);
			}
		}
		
		//da li je zauzeta lokacija taj dan?
		// da li je datum prosao?
		if(event.getEventDays() != null) {
			for (EventDay day : event.getEventDays()) {
				if(day.getEventDate().before(new Date())) {
					throw new DateInThePastException();
				}
				if (!dayService.checkAvailability(day.getEventDate(), location.getId())) {
					throw new LocationNotAvailableException(location.getId(), day.getEventDate());
				}
			}
		}

		event.setId(null);
		Event e = eventService.saveEvent(event);

		return new ResponseEntity<EventDTO>(EventDTOConverter.eventToDto(e), HttpStatus.CREATED);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EventDTO> updateEvent(@RequestBody EventDTO dto) {

		if (dto.getId() == null) {
			throw new IdNotFoundException();
		}

		Event found = eventService.findOne(dto.getId());

		if (found == null) {
			throw new EventNotFoundException(dto.getId());
		}

		Event event = EventDTOConverter.dtoToEvent(dto);
		Location location = locationService.findOne(dto.getLocationID());
		event.setLocation(location);

		Event e = eventService.updateEvent(event);

		return new ResponseEntity<EventDTO>(EventDTOConverter.eventToDto(e), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{eventId}")
	public ResponseEntity<Void> deactivateEvent(@PathVariable("eventId") Long id) {

		Event event = eventService.findOne(id);

		if (event == null) {
			throw new EventNotFoundException(id);
		} else {

			if (!event.getTickets().isEmpty())
				return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);

			boolean isActive = event.getIsActive();
			event.setIsActive(!isActive);

			Event e = eventService.updateEvent(event);
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

		if (event == null) {
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
		Error error = new Error(1, "Event [" + eventId + "] not found");
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(SectorNotBelongLocationException.class)
	public ResponseEntity<Error> sectorNotFound(SectorNotBelongLocationException e) {
		long sectorId = e.getSectorId();

		Error error = new Error(1, "Sector [" + sectorId + "] not found in location");
		return new ResponseEntity<Error>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(LocationNotAvailableException.class)
	public ResponseEntity<Error> locationNotAvailable(LocationNotAvailableException e) {
		long locationId = e.getLocationId();
		Date date = e.getEventDate();

		Error error = new Error(1, "Location [" + locationId + "] is not available on date " + date);
		return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(DateInThePastException.class)
	public ResponseEntity<Error> dateInThePast(DateInThePastException e) {
		Error error = new Error(1, "Date has already past");
		return new ResponseEntity<Error>(error, HttpStatus.EXPECTATION_FAILED);
	}
}
