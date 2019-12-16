package Tim20.KTS_NVT.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.h2.engine.SysProperties;
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

import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.service.SectorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LocationControllerIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private SectorService sectorService;

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
	public void deleteSectorTest() {

		Sector sector = new StandSector();
		sector.setTitle("Sector for delete");

		Sector saved = sectorService.saveSector(sector);

		int size = sectorService.findAll().size();

		String uri = "/locations/sector/" + saved.getId();
		ResponseEntity<Void> responseEntity = restTemplate.exchange(uri, HttpMethod.DELETE,
				new HttpEntity<Object>(null), Void.class);

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
