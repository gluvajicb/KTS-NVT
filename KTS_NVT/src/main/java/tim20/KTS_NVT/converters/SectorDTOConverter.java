package tim20.KTS_NVT.converters;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tim20.KTS_NVT.dto.SectorDTO;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;

public class SectorDTOConverter {

	public static StandSector dtoToStandSector(SectorDTO dto, Location location) {

		StandSector sector = new StandSector();

		sector.setTitle(dto.getTitle());
		sector.setMaxGuests(dto.getMax_guests());
		sector.setLocation(location);
		sector.setId(dto.getId());
		sector.setAngle(dto.getAngle());
		sector.setHeight(dto.getHeight());
		sector.setWidth(dto.getWidth());
		sector.setTop(dto.getTop());
		sector.setPosLeft(dto.getLeft());

		return sector;
	}

	public static SectorDTO standSectorToDto(StandSector ss) {

		SectorDTO dto = new SectorDTO(ss.getId(), ss.getTitle(), "Stand", ss.getMaxGuests(), -1, -1, ss.getTop(), ss.getPosLeft(), ss.getHeight(), ss.getWidth(), ss.getAngle());

		return dto;
	}

	public static SeatsSector dtoToSeatsSector(SectorDTO dto, Location location) {

		SeatsSector sector = new SeatsSector();

		sector.setTitle(dto.getTitle());
		sector.setRowNum(dto.getRow_num());
		sector.setColumnNum(dto.getColumn_num());
		sector.setLocation(location);
		sector.setId(dto.getId());
		sector.setAngle(dto.getAngle());
		sector.setHeight(dto.getHeight());
		sector.setWidth(dto.getWidth());
		sector.setTop(dto.getTop());
		sector.setPosLeft(dto.getLeft());

		return sector;
	}

	public static SectorDTO seatsSectorToDto(SeatsSector ss) {

		SectorDTO dto = new SectorDTO(ss.getId(), ss.getTitle(), "Seats", -1, ss.getRowNum(), ss.getColumnNum(), ss.getTop(), ss.getPosLeft(), ss.getHeight(), ss.getWidth(), ss.getAngle());

		return dto;
	}

	public static List<SectorDTO> convertSectorsToDtos(Set<Sector> sectors) {

		List<SectorDTO> retVal = new ArrayList<SectorDTO>();

		for (Sector sector : sectors) {
			if (sector instanceof SeatsSector) {
				retVal.add(SectorDTOConverter.seatsSectorToDto((SeatsSector) sector));
			} else {
				retVal.add(SectorDTOConverter.standSectorToDto((StandSector) sector));
			}
		}

		return retVal;
	}
	
	public static Set<Sector> convertDTOsToSectors(List<SectorDTO> dtos, Location location) {
		Set<Sector> retVal = new HashSet<Sector>();
		
		for (SectorDTO dto : dtos) {
			if(dto.getType().equalsIgnoreCase("seats")) {
				retVal.add(SectorDTOConverter.dtoToSeatsSector(dto, location));
			}else {
				retVal.add(SectorDTOConverter.dtoToStandSector(dto, location));
			}
		}
		
		return retVal;
	}
}
