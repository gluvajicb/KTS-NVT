package Tim20.KTS_NVT.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.repository.SectorRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class SectorRepositoryIntegrationTest {

	@Autowired
	private SectorRepository sectorRepository;

	@Test
	public void testFindByLocationId() {
		
		final long val = 1;
		
		List<Sector> sectors = sectorRepository.findByLocationId(val);
		
		assertEquals(3, sectors.size());

		for (Sector sector : sectors) {
			assertEquals(val, sector.getLocation().getId().longValue());
		}
		
	}
	
	@Test
	public void testFindByLocationIdNotFound() {
		
		
		List<Sector> sectors = sectorRepository.findByLocationId(111l);
		
		assertTrue(sectors.size() == 0);
		
	}
}
