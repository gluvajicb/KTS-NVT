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
		
		assertTrue(sectors.size() ==  4);
	}
	
	@Test
	public void findOneTestStand() {
		// za stand sektor
		Sector sector = sectorService.findOne(101l);
		
		assertNotNull(sector);
		assertTrue(sector instanceof StandSector);
		assertEquals(101l, sector.getId());
		assertEquals("Stand sector", sector.getTitle());
		assertEquals(100, ((StandSector)sector).getMaxGuests());
		assertEquals(1, sector.getLocation().getId());
	}
	
	@Test
	public void findOneTestSeats() {
		// za seats sektor
		Sector sector = sectorService.findOne(104l);
		
		assertNotNull(sector);
		assertTrue(sector instanceof SeatsSector);
		assertEquals(104l, sector.getId());
		assertEquals("Seats sector 2", sector.getTitle());
		assertEquals(10, ((SeatsSector)sector).getColumnNum());
		assertEquals(2, sector.getLocation().getId());
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
		sectorRepository.deleteById(saved.getId());
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
		Sector sector = new SeatsSector();
		Sector saved = sectorRepository.save(sector);
		int size = sectorRepository.findAll().size();
		sectorService.deleteSector(saved.getId());
		
		assertEquals(size - 1, sectorRepository.findAll().size());
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void deleteSectorTestException() {
		sectorService.deleteSector(444l);
	}
}
