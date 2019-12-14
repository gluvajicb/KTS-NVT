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
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.junit4.SpringRunner;

import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.service.LocationService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class LocationServiceIntegrationTest {

	@Autowired
	private LocationService locationService;

	@Test
	public void findAllTest() {

		List<Location> locations = locationService.findAll();

		assertNotNull(locations);
		assertTrue(locations.size() > 0);
	}

	@Test
	public void findOneTest() {

		Location location = locationService.findOne(1l);

		assertNotNull(location);
		assertEquals(1l, location.getId());
	}

	@Test
	public void findOneNotFoundTest() {

		Location location = locationService.findOne(1456l);

		assertNull(location);
	}

	@Test
	public void saveLocationTest() {

		Location location = new Location();
		location.setTitle("My new location");

		Location saved = locationService.saveLocation(location);

		assertNotNull(saved);
		assertNotNull(saved.getId());
		assertEquals(location.getTitle(), saved.getTitle());

	}

	@Test
	public void saveLocationWithId() {
		Location location = new Location();
		location.setId(352l);

		assertNull(locationService.saveLocation(location));

	}

	@Test
	public void updateLocationTest() {

		Location location = new Location();
		location.setId(1l);
		location.setTitle("My new location");

		Location updated = locationService.updateLocation(location);

		assertNotNull(updated);
		assertEquals(location.getId(), updated.getId());
		assertEquals(location.getTitle(), updated.getTitle());

	}

	@Test
	public void updateLocationWithoutId() {

		Location location = new Location();

		assertNull(locationService.updateLocation(location));

	}

	@Test
	public void deleteLocation() {
		locationService.deleteLocation(2l);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void deleteLocationTestException() {
		locationService.deleteLocation(444l);
	}

	@Test
	public void findSectorTest() {
		Sector sector = locationService.findSector(101l);

		assertNotNull(sector);
		assertEquals(101l, sector.getId());

	}

	@Test
	public void findSectorNotFoundTest() {
		Sector sector = locationService.findSector(5876l);

		assertNull(sector);

	}

	@Test
	public void deleteSectorTest() {
		locationService.deleteSector(103l);
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void deleteSectorTestException() {
		locationService.deleteSector(444l);
	}
}
