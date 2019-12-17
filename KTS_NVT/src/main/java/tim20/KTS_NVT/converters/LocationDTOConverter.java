package tim20.KTS_NVT.converters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tim20.KTS_NVT.dto.LocationDTO;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;

public class LocationDTOConverter {

	public static Location dtoToLocation(LocationDTO dto) {

		Location location = new Location();

		location.setAddress(dto.getAddress());
		location.setId(dto.getId());
		location.setTitle(dto.getTitle());

		return location;

	}

	public static LocationDTO locationToDto(Location location) {

		LocationDTO dto = new LocationDTO(location.getId(), location.getTitle(), location.getAddress());

		for (Sector sector : location.getSectors()) {
			if (sector instanceof SeatsSector) {
				dto.getSectors().add(SectorDTOConverter.seatsSectorToDto((SeatsSector)sector));
			}else {
				dto.getSectors().add(SectorDTOConverter.standSectorToDto((StandSector)sector));
			}
		}

		return dto;
	}
	
	public static List<LocationDTO> locationsToDtos(Collection<Location> locations){
		
		List<LocationDTO> retVal = new ArrayList<LocationDTO>();

		for (Location location : locations) {
			retVal.add(LocationDTOConverter.locationToDto(location));
		}

		return retVal;
	}
}
