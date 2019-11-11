package tim20.KTS_NVT.controller;

import java.util.Collection;

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

import tim20.KTS_NVT.dto.LocationDTO;
import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.exceptions.LocationNotFoundException;
import tim20.KTS_NVT.exceptions.LowerThanZeroException;
import tim20.KTS_NVT.exceptions.SectorNotFoundException;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.service.LocationService;

@Controller
@RequestMapping(value = "/locations")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Location>> getAll() {

		Collection<Location> locations = locationService.findAll();

		return new ResponseEntity<Collection<Location>>(locations, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Location> getOne(@PathVariable("id") Long id) {

		Location location = locationService.findOne(id);

		if (location == null) {
			throw new LocationNotFoundException(id);
		} else {
			return new ResponseEntity<Location>(location, HttpStatus.OK);
		}

	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Location> addLocation(@RequestBody LocationDTO dto) {
		// location.setId(null);
		Location location = locationService.saveLocation(dto);

		return new ResponseEntity<Location>(location, HttpStatus.OK);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Location> updateLocation(@PathVariable("id") Long id, @RequestBody LocationDTO dto) {

		Location location = locationService.updateLocation(dto);

		if (location == null) {
			throw new LocationNotFoundException(id);
		} else {
			return new ResponseEntity<Location>(location, HttpStatus.OK);
		}

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteLocation(@PathVariable("id") Long id) {

		Location location = locationService.findOne(id);

		if (location == null) {
			throw new LocationNotFoundException(id);
		} else {
			locationService.deleteLocation(id);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}

	}

	// ******************************** sektori *****************************

	@GetMapping(value = "/{locationId}/sectors")
	public ResponseEntity<Collection<Sector>> getAllSectorsByLocation(@PathVariable("locationId") Long locationId) {

		Location location = locationService.findOne(locationId);

		if (location == null) {
			throw new LocationNotFoundException(locationId);
		} else {
			return new ResponseEntity<Collection<Sector>>(location.getSectors(), HttpStatus.OK);
		}

	}

	@PostMapping(value = "/{locationId}/sectors", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Location> addNewSector(@PathVariable("locationId") Long locationId,
			@RequestBody SectorDTO dto) {

		Location location = locationService.findOne(locationId);

		if (location == null) {
			throw new LocationNotFoundException(locationId);
		} else {

			if (!dto.getType().equalsIgnoreCase("seats") && !dto.getType().equalsIgnoreCase("stand")) {
				if (dto.getColumn_num() > 0 && dto.getRow_num() > 0) {
					dto.setType("seats");
					locationService.saveSector(location, dto);
				} else if (dto.getMax_guests() > 0) {
					dto.setType("stand");
					locationService.saveSector(location, dto);
				} else {
					throw new LowerThanZeroException();
				}
			} else if ((dto.getType().equalsIgnoreCase("seats") && (dto.getRow_num() < 0 || dto.getColumn_num() < 0))
					|| (dto.getType().equalsIgnoreCase("stand") && dto.getMax_guests() < 0)) {
				throw new LowerThanZeroException();
			} else {
				locationService.saveSector(location, dto);
			}

		}

		return new ResponseEntity<Location>(location, HttpStatus.OK);

	}

	@DeleteMapping(value = "/sector/{sectorId}")
	public ResponseEntity<Void> deleteSector(@PathVariable("sectorId") Long id) {

		Sector sector = locationService.findSector(id);

		if (sector == null) {
			throw new SectorNotFoundException(id);
		} else {
			locationService.deleteSector(id);
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

}
