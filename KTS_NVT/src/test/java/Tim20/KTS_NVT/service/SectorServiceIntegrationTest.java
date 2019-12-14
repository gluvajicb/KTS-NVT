package Tim20.KTS_NVT.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.service.SectorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SectorServiceIntegrationTest {
	
	@Autowired
	SectorService sectorService;
	
	@Test
	public void findAllTest() {
		List<Sector> sectors = sectorService.findAll();
		
		assertNotNull(sectors);
		assertTrue(sectors.size() > 0);
	}
	
	@Test
	public void findOneTest() {
		
		Sector sector = sectorService.findOne(101l);
		
		assertNotNull(sector);
		assertEquals(101l, sector.getId());
		
	}
	
	@Test
	public void findOneNotFoundTest() {
		
		Sector sector = sectorService.findOne(789l);
		
		assertNull(sector);
	}
	
	@Test
	public void saveSectorTest() {
		
		Sector sector = new StandSector();
		sector.setTitle("New stand sector");
		
		Sector saved = sectorService.saveSector(sector);
		
		assertNotNull(saved);
		assertNotNull(saved.getId());
		
	}

	@Test
	public void saveSectorIdNotNull() {
		
		Sector sector = new SeatsSector();
		sector.setId(456l);
		Sector saved = sectorService.saveSector(sector);
		
		assertNull(saved);
	}
}
