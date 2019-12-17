package Tim20.KTS_NVT.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.repository.SectorRepository;
import tim20.KTS_NVT.service.SectorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SectorServiceIntegrationTest {
	
	@Autowired
	SectorService sectorService;
	
	@Autowired
	SectorRepository sectorRepository;
	
	@Test
	public void findAllTest() {
		List<Sector> sectors = sectorService.findAll();
		
		assertNotNull(sectors);
		System.out.println(sectors.size());
		
		for (Sector sector : sectors) {
			System.out.println(sector.getId());
			System.out.println(sector.getTitle());
		}
		assertTrue(sectors.size() > 0 );
	}
	
	@Test
	public void findOneTest() {
		
		Sector sector = sectorService.findOne(101l);
		
		assertNotNull(sector);
		assertEquals(101l, sector.getId());
		assertEquals("Stand sector", sector.getTitle());
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
		
		Optional<Sector> find = sectorRepository.findById(saved.getId());
		
		assertTrue(find.isPresent());
		assertEquals(saved.getId(), find.get().getId());
		
	}

	@Test
	public void saveSectorIdNotNull() {
		
		Sector sector = new SeatsSector();
		sector.setId(456l);
		Sector saved = sectorService.saveSector(sector);
		
		assertNull(saved);
	}
	
	@Test
	public void deleteSectorTest() {
		sectorService.deleteSector(103l);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void deleteSectorTestException() {
		sectorService.deleteSector(444l);
	}
}
