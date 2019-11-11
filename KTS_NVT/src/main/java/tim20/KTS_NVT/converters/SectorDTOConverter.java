package tim20.KTS_NVT.converters;

import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.StandSector;

public class SectorDTOConverter {

	public static StandSector dtoToStandSector(SectorDTO dto, Location location) {
		
		StandSector sector = new StandSector();
		
		sector.setTitle(dto.getTitle());
		sector.setMaxGuests(dto.getMax_guests());
		sector.setLocation(location);
		
		return sector;
	}
	
public static SeatsSector dtoToSeatsSector(SectorDTO dto, Location location) {
		
		SeatsSector sector = new SeatsSector();
		
		sector.setTitle(dto.getTitle());
		sector.setRowNum(dto.getRow_num());
		sector.setColumnNum(dto.getColumn_num());
		sector.setLocation(location);
		
		return sector;
	}

}
