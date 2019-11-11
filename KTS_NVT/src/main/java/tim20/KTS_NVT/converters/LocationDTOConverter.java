package tim20.KTS_NVT.converters;

import tim20.KTS_NVT.dto.LocationDTO;
import tim20.KTS_NVT.model.Location;

public class LocationDTOConverter {

	public static Location dtoToLocation(LocationDTO dto) {
		
		Location location = new Location();
		
		location.setAddress(dto.getAddress());
		location.setId(dto.getId());
		location.setTitle(dto.getTitle());
		
		return location;
		
	}
}
