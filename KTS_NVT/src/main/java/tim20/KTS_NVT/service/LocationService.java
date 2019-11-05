package tim20.KTS_NVT.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.repository.LocationRepository;
import tim20.KTS_NVT.repository.SectorRepository;

@Service
public class LocationService {

	@Autowired
	private LocationRepository locationRepository;

	@Autowired
	private SectorRepository sectorRepository;

	public List<Location> findAll() {

		List<Location> locations = locationRepository.findAll();

		return locations;
	}

	/*
	 * public List<Sector> getSectorsByLocation(Long locationId){ List<Sector>
	 * sectors = sectorRepository.findByLocationId(locationId); return sectors; }
	 */

	public Location findOne(Long id) {

		Optional<Location> location = locationRepository.findById(id);

		if (location.isPresent()) {
			return location.get();
		}

		return null;
	}

	public Location saveLocation(Location location) {

		Location l = locationRepository.save(location);

		return l;

	}

	public Location saveSector(Location location, SectorDTO dto) {

		if (dto.getType().equalsIgnoreCase("seats")) {
			SeatsSector seatsSector;
			seatsSector = new SeatsSector();
			seatsSector.setRowNum(dto.getRow_num());
			seatsSector.setTitle(dto.getTitle());
			seatsSector.setColumnNum(dto.getColumn_num());
			seatsSector.setLocation(location);
			location.getSectors().add(seatsSector);
			sectorRepository.save(seatsSector);
		} else {
			StandSector standSector;
			standSector = new StandSector();
			standSector.setTitle(dto.getTitle());
			standSector.setMaxGuests(dto.getMax_guests());
			standSector.setLocation(location);

			sectorRepository.save(standSector);
		}

		return location;

	}

	public Sector findSector(Long sectorId) {
		Optional<Sector> sector = sectorRepository.findById(sectorId);

		if (sector.isPresent()) {
			return sector.get();
		}

		return null;
	}

	public void deleteSector(Long sectorId) {
		sectorRepository.deleteById(sectorId);
	}

	public void deleteLocation(Long locationId) {
		locationRepository.deleteById(locationId);
	}
}
