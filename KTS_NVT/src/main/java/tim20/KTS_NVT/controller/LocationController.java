package tim20.KTS_NVT.controller;

import java.util.Collection;
import java.util.Date;
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

import tim20.KTS_NVT.converters.LocationDTOConverter;
import tim20.KTS_NVT.converters.SectorDTOConverter;
import tim20.KTS_NVT.dto.LocationDTO;
import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.exceptions.IdNotFoundException;
import tim20.KTS_NVT.exceptions.LocationCanNotBeDeletedException;
import tim20.KTS_NVT.exceptions.LocationNotFoundException;
import tim20.KTS_NVT.exceptions.LowerThanZeroException;
import tim20.KTS_NVT.exceptions.SectorCanNotBeDeletedException;
import tim20.KTS_NVT.exceptions.SectorNotFoundException;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.EventDay;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.service.LocationService;
import tim20.KTS_NVT.service.SectorService;

@Controller
@CrossOrigin
@RequestMapping(value = "/locations")
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	@Autowired
	private SectorService sectorService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LocationDTO>> getAll() {

		Collection<Location> locations = locationService.findAll();
		
		List<LocationDTO> dtos = LocationDTOConverter.locationsToDtos(locations);

		return new ResponseEntity<List<LocationDTO>>(dtos, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationDTO> getOne(@PathVariable("id") Long id) {

		Location location = locationService.findOne(id);

		if (location == null) {
			throw new LocationNotFoundException(id);
		} else {
			LocationDTO dto = LocationDTOConverter.locationToDto(location);
			return new ResponseEntity<LocationDTO>(dto, HttpStatus.OK);
		}

	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationDTO> addLocation(@RequestBody LocationDTO dto) {
		
		Location location = LocationDTOConverter.dtoToLocation(dto);
		location.setId(null);
		Location l = locationService.saveLocation(location);

		return new ResponseEntity<LocationDTO>(LocationDTOConverter.locationToDto(l), HttpStatus.CREATED);
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LocationDTO> updateLocation(@RequestBody LocationDTO dto) {

		if(dto.getId() == null) {
			throw new IdNotFoundException();
		}
		
		Location found = locationService.findOne(dto.getId());
		
		if(found == null) {
			throw new LocationNotFoundException(dto.getId());
		}
		
		Location location = LocationDTOConverter.dtoToLocation(dto);
		
		Location l = locationService.updateLocation(location);

		return new ResponseEntity<LocationDTO>(LocationDTOConverter.locationToDto(l), HttpStatus.OK);
	

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable("id") Long id) {

		Location location = locationService.findOne(id);

		if (location == null) {
			throw new LocationNotFoundException(id);
		} else {
			
			if (location.getEvents().size() > 0) {
				for (Event event : location.getEvents()) {
					for (EventDay day : event.getEventDays()) {
						if (day.getEventDate().after(new Date())) {
							throw new LocationCanNotBeDeletedException(id);
						}
					}
				}
			}
	
			locationService.deleteLocation(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}

	}

	// ******************************** sektori *****************************

	@GetMapping(value = "/{locationId}/sectors")
	public ResponseEntity<Collection<SectorDTO>> getAllSectorsByLocation(@PathVariable("locationId") Long locationId) {

		Location location = locationService.findOne(locationId);

		if (location == null) {
			throw new LocationNotFoundException(locationId);
		} else {
			List<SectorDTO> dtos = SectorDTOConverter.convertSectorsToDtos(location.getSectors());
			return new ResponseEntity<Collection<SectorDTO>>(dtos, HttpStatus.OK);
		}

	}

	@PostMapping(value = "/{locationId}/sectors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SectorDTO> addNewSector(@PathVariable("locationId") Long locationId,
			@RequestBody SectorDTO dto) {

		Location location = locationService.findOne(locationId);
		SectorDTO sectorDTO = null;
		
		if (location == null) {
			throw new LocationNotFoundException(locationId);
		} else {

			if (!dto.getType().equalsIgnoreCase("seats") && !dto.getType().equalsIgnoreCase("stand")) {
				if (dto.getColumn_num() > 0 && dto.getRow_num() > 0) {
					dto.setType("seats");
					sectorDTO = SectorDTOConverter.seatsSectorToDto((SeatsSector)locationService.saveSector(location, dto));
				} else if (dto.getMax_guests() > 0) {
					dto.setType("stand");
					sectorDTO = SectorDTOConverter.standSectorToDto((StandSector)locationService.saveSector(location, dto));
				} else {
					throw new LowerThanZeroException();
				}
			} else if ((dto.getType().equalsIgnoreCase("seats") && (dto.getRow_num() <= 0 || dto.getColumn_num() <= 0))
					|| (dto.getType().equalsIgnoreCase("stand") && dto.getMax_guests() <= 0)) {
				throw new LowerThanZeroException();
			} else {
				Sector newSector = null;
				
				if(dto.getType().equalsIgnoreCase("seats")) {
					newSector = sectorService.saveSector(SectorDTOConverter.dtoToSeatsSector(dto, location));
					sectorDTO = SectorDTOConverter.seatsSectorToDto((SeatsSector)newSector);
				}else {
					newSector = sectorService.saveSector(SectorDTOConverter.dtoToStandSector(dto, location));
					sectorDTO = SectorDTOConverter.standSectorToDto((StandSector)newSector);
				}
				//sector = sectorService.saveSector(location, dto);
			}

		}

		return new ResponseEntity<SectorDTO>(sectorDTO, HttpStatus.CREATED);

	}

	@DeleteMapping(value = "/sector/{sectorId}")
	public ResponseEntity<Void> deleteSector(@PathVariable("sectorId") Long id) {

		Sector sector = locationService.findSector(id);

		if (sector == null) {
			throw new SectorNotFoundException(id);
		} else {
			Location location = sector.getLocation();
			if(location.getEvents() != null) {
				if (location.getEvents().size() > 0) {
					for (Event event : location.getEvents()) {
						for (EventDay day : event.getEventDays()) {
							if (day.getEventDate().after(new Date())) {
								throw new SectorCanNotBeDeletedException(id);
							}
						}
					}
				}
			}
			
			sectorService.deleteSector(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}

	}

	// ***************************Exception handler******************************

	@ExceptionHandler(LocationNotFoundException.class)
	public ResponseEntity<Error> locationNotFound(LocationNotFoundException e) {
		long locationId = e.getLocationId();
		Error error = new Error(1, "Location [" + locationId + "] not found");
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(LowerThanZeroException.class)
	public ResponseEntity<Error> numberLowerThanZero(LowerThanZeroException e) {
		Error error = new Error(1, "Number must be greater than zero");
		return new ResponseEntity<Error>(error, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(SectorNotFoundException.class)
	public ResponseEntity<Error> sectorNotFound(SectorNotFoundException e) {
		long locationId = e.getSectorId();
		Error error = new Error(1, "Sector [" + locationId + "] not found");
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<Error> idNotFound(IdNotFoundException e) {
		Error error = new Error(1, "Id not found");
		return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(LocationCanNotBeDeletedException.class)
	public ResponseEntity<Error> locationCnNotBeDeleted(LocationCanNotBeDeletedException e) {
		Error error = new Error(1, "Location [" + e.getLocationId() + "] can not be deleted");
		return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(SectorCanNotBeDeletedException.class)
	public ResponseEntity<Error> sectorCanNotBeDeleted(LocationCanNotBeDeletedException e) {
		Error error = new Error(1, "Sector [" + e.getLocationId() + "] can not be deleted");
		return new ResponseEntity<Error>(error, HttpStatus.CONFLICT);
	}

}
