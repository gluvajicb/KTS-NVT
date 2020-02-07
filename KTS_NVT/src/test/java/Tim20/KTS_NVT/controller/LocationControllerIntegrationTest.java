package Tim20.KTS_NVT.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.dto.LocationDTO;
import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.service.LocationService;
import tim20.KTS_NVT.service.SectorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LocationControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private SectorService sectorService;
	
	@Autowired
	private LocationService locationService;

	@Test
	public void getOneTest() {
		ResponseEntity<LocationDTO> responseEntity = restTemplate.getForEntity("/locations/2", LocationDTO.class);

		LocationDTO location = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(location);
		assertEquals("Location 2", location.getTitle());
	}

	@Test
	public void getOneNotFoundTest() {
		ResponseEntity<Error> responseEntity = restTemplate.getForEntity("/locations/256", Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Location [256] not found", error.getMessage());
	}

	@Test
	public void getAllTest() {
		ResponseEntity<LocationDTO[]> responseEntity = restTemplate.getForEntity("/locations", LocationDTO[].class);

		LocationDTO[] locations = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3, locations.length);

	}
	
	@Test
	public void addNewLocationTest() {

		int size = locationService.findAll().size();

		ResponseEntity<LocationDTO> responseEntity = restTemplate.postForEntity("/locations",
				new LocationDTO(null, "My new location","My new address"), LocationDTO.class);

		LocationDTO dto = responseEntity.getBody();

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(dto);
		assertEquals("My new location", dto.getTitle());
		assertEquals("My new address", dto.getAddress());

		List<Location> locations = locationService.findAll();
		assertEquals(size + 1, locations.size());

		locationService.deleteLocation(dto.getId());

	}
	
	@Test
    public void updateLocationTest() {
    	ResponseEntity<LocationDTO> responseEntity =
	            restTemplate.exchange("/locations", HttpMethod.PUT, 
	            		new HttpEntity<LocationDTO>(new LocationDTO(3L, "Updated title", "Updated address")),
	            		LocationDTO.class);
	            
    	LocationDTO location = responseEntity.getBody();
    	
    	assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertNotNull(location);
		assertEquals(Long.valueOf(3L), location.getId());
        assertEquals("Updated title", location.getTitle());
        assertEquals("Updated address", location.getAddress());
        
        Location dbLocation = locationService.findOne(3L);
        assertEquals(Long.valueOf(3L), dbLocation.getId());
        assertEquals("Updated title", dbLocation.getTitle());
        assertEquals("Updated address", dbLocation.getAddress());
        
        // vracanje podatka na staru vrednost
        dbLocation.setTitle("Location 3");
        dbLocation.setAddress("Address 3");
        locationService.saveLocation(dbLocation);
    }
	
	@Test
	public void updateLocationNotFoundTest() {
		ResponseEntity<Error> responseEntity = restTemplate.exchange("/locations", HttpMethod.PUT, 
        		new HttpEntity<LocationDTO>(new LocationDTO(150L, "Updated title", "Updated address")),
        		Error.class);
		
		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Location [150] not found", error.getMessage());
	}
	
	@Test
	public void updateLocationIdNotFoundTest() {
		ResponseEntity<Error> responseEntity = restTemplate.exchange("/locations", HttpMethod.PUT, 
        		new HttpEntity<LocationDTO>(new LocationDTO(null, "Updated title", "Updated address")),
        		Error.class);
		
		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Id not found", error.getMessage());
	}
	
	
	@Test
	public void deleteLocationTest() {

		Location location = new Location();
		location.setTitle("Location for delete");

		Location saved = locationService.saveLocation(location);

		int size = locationService.findAll().size();

		String uri = "/locations/" + saved.getId();
		ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE,
				new HttpEntity<Object>(null), Void.class);

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, locationService.findAll().size());

	}

	@Test
	public void deleteLocationNotFoundTest() {

		ResponseEntity<Error> responseEntity = restTemplate.exchange("/locations/115", HttpMethod.DELETE,
				new HttpEntity<Object>(null), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Location [115] not found", error.getMessage());
	}
	
	@Test
	public void deleteLocationHasEventsTest() {

		ResponseEntity<Error> responseEntity = restTemplate.exchange("/locations/2", HttpMethod.DELETE,
				new HttpEntity<Object>(null), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Location [2] can not be deleted", error.getMessage());
	}

	// ****************** sektori ****************
	@Test
	public void getAllSectorsByLocationTest() {

		ResponseEntity<SectorDTO[]> responseEntity = restTemplate.getForEntity("/locations/1/sectors",
				SectorDTO[].class);

		SectorDTO[] sectors = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(3, sectors.length);
	}

	@Test
	public void getAllSectorsByLocationNotFoundTest() {

		ResponseEntity<Error> responseEntity = restTemplate.getForEntity("/locations/15/sectors", Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Location [15] not found", error.getMessage());
	}

	@Test
	public void getAllSectorsByLocationNoSectorsTest() {

		ResponseEntity<SectorDTO[]> responseEntity = restTemplate.getForEntity("/locations/3/sectors",
				SectorDTO[].class);

		SectorDTO[] sectors = responseEntity.getBody();

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(0, sectors.length);
	}

	@Test
	public void addNewSectorSeatsTest() {

		int size = sectorService.findAll().size();

		ResponseEntity<SectorDTO> responseEntity = restTemplate.postForEntity("/locations/2/sectors",
				new SectorDTO(null, "My new sector", "seats", 0, 15, 30, 100, 100, 100, 100, 0), SectorDTO.class);

		SectorDTO sector = responseEntity.getBody();

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(sector);
		assertEquals("My new sector", sector.getTitle());
		assertEquals("seats", sector.getType().toLowerCase());

		List<Sector> sectors = sectorService.findAll();
		assertEquals(size + 1, sectors.size());

		sectorService.deleteSector(sector.getId());

	}

	@Test
	public void addNewSectorStandTest() {

		int size = sectorService.findAll().size();

		ResponseEntity<SectorDTO> responseEntity = restTemplate.postForEntity("/locations/2/sectors",
				new SectorDTO(null, "My new sector", "stand", 150, 0, 0, 100, 100, 100, 100, 0), SectorDTO.class);

		SectorDTO sector = responseEntity.getBody();

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertNotNull(sector);
		assertEquals("My new sector", sector.getTitle());
		assertEquals("stand", sector.getType().toLowerCase());

		List<Sector> sectors = sectorService.findAll();
		assertEquals(size + 1, sectors.size());

		sectorService.deleteSector(sector.getId());

	}

	@Test
	public void addNewSectorLocationNotFoundTest() {

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/locations/456/sectors",
				new SectorDTO(null, "My new sector", "stand", 150, 0, 0, 100, 100, 100, 100, 0), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals("Location [456] not found", error.getMessage());
		assertEquals(1, error.getCode());
	}

	@Test
	public void addNewSectorLowerThanZeroStand() {

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/locations/2/sectors",
				new SectorDTO(null, "My new sector", "stand", 0, 0, 0, 100, 100, 100, 100, 0), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.EXPECTATION_FAILED, responseEntity.getStatusCode());
		assertEquals("Number must be greater than zero", error.getMessage());
		assertEquals(1, error.getCode());
	}

	@Test
	public void addNewSectorLowerThanZeroSeats() {

		ResponseEntity<Error> responseEntity = restTemplate.postForEntity("/locations/2/sectors",
				new SectorDTO(null, "My new sector", "seats", 0, 0, 0, 100, 100, 100, 100, 0), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.EXPECTATION_FAILED, responseEntity.getStatusCode());
		assertEquals("Number must be greater than zero", error.getMessage());
		assertEquals(1, error.getCode());
	}

	@Test
	public void deleteSectorTest() {

		Sector sector = new StandSector();
		Location location = locationService.saveLocation(new Location());
		
		sector.setTitle("Sector for delete");
		sector.setLocation(location);
		Sector saved = sectorService.saveSector(sector);

		
		int size = sectorService.findAll().size();

		String uri = "/locations/sector/" + saved.getId();
		ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE,
				new HttpEntity<Object>(null), Void.class);
		
		locationService.deleteLocation(location.getId());

		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(size - 1, sectorService.findAll().size());

	}

	@Test
	public void deleteSectorNotFoundTest() {

		ResponseEntity<Error> responseEntity = restTemplate.exchange("/locations/sector/115", HttpMethod.DELETE,
				new HttpEntity<Object>(null), Error.class);

		Error error = responseEntity.getBody();

		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
		assertEquals(1, error.getCode());
		assertEquals("Sector [115] not found", error.getMessage());
	}
}
