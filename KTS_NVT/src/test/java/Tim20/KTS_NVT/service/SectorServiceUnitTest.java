package Tim20.KTS_NVT.service;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.repository.SectorRepository;
import tim20.KTS_NVT.service.SectorService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class SectorServiceUnitTest {

	@Autowired
	private SectorService sectorService;
	
	@MockBean
	private SectorRepository sectorRepositoryMocked;
	
	@Before
	public void setUp() {
		SeatsSector seats = new SeatsSector();
		seats.setId(10l);
		seats.setTitle("My seats sector");
		Optional<Sector> optss = Optional.of(seats);
		Mockito.when(sectorRepositoryMocked.findById(seats.getId())).thenReturn(optss);
	
		StandSector stand = new StandSector();
		stand.setTitle("My stand sector");
		
		List<Sector> myList = new ArrayList<Sector>();
		myList.add(seats);
		myList.add(stand);
		Mockito.when(sectorRepositoryMocked.findAll()).thenReturn(myList);
		
		Optional<Sector> optEmpty = Optional.empty();
		Mockito.when(sectorRepositoryMocked.findById(100l)).thenReturn(optEmpty);

	}
	
	@Test
	public void testFindAll(){
		assertTrue(sectorService.findAll().size() == 2);
	}
	
	@Test
	public void testFindOne(){
		Sector sector = sectorService.findOne(10l);
		assertEquals("My seats sector", sector.getTitle());
	}
	
	@Test
	public void testFindOneEmpty(){
		Sector sector = sectorService.findOne(100l);
		assertNull(sector);
	}
	
	@Test
	public void testSave() {
		// Kako ovo testirati?
		
		StandSector ss = new StandSector();
		ss.setTitle("My sector to save");
		
		Mockito.when(sectorRepositoryMocked.save(ss)).thenReturn(ss);
		
		Sector saved = sectorService.saveSector(ss);
		
		assertEquals("My sector to save", saved.getTitle());
	}
	
	@Test
	public void testSaveWithId() {
		
		StandSector ss = new StandSector();
		ss.setId(1l);
		assertNull(sectorService.saveSector(ss));
	}
}
