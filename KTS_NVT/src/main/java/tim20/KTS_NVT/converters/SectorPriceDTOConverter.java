package tim20.KTS_NVT.converters;

import tim20.KTS_NVT.dto.EventDayDTO;
import tim20.KTS_NVT.dto.SectorPriceDTO;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.SeatsSector;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.model.StandSector;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.service.SectorService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SectorPriceDTOConverter {

    public static SectorPrice dtoToSectorPrice(SectorPriceDTO dto)
    {
        //EventService eventService = new EventService();
        //SectorService sectorService = new SectorService();

        SectorPrice sectorprice = new SectorPrice();

        sectorprice.setPrice(dto.getPrice());
        
        //sectorprice.setSector(sectorService.findOne(dto.getSectorID()));
        //sectorprice.setEvent(eventService.findOne(dto.getEventID()));

        return sectorprice;

    }

    public static SectorPriceDTO sectorpriceToDto(SectorPrice sectorprice) {
    	SectorPriceDTO dto;
    	if(sectorprice.getSector() instanceof SeatsSector) {
    		SeatsSector ss = (SeatsSector) sectorprice.getSector();
    		dto = new SectorPriceDTO(sectorprice.getId(), sectorprice.getPrice(), SectorDTOConverter.seatsSectorToDto(ss), sectorprice.getEvent().getId());
    	} else {
    		StandSector ss = (StandSector) sectorprice.getSector();
    		Long eId = null;
    		if(sectorprice.getEvent() != null) {
    			eId = sectorprice.getEvent().getId();
    		}
    		dto = new SectorPriceDTO(sectorprice.getId(), sectorprice.getPrice(), SectorDTOConverter.standSectorToDto(ss), eId);
    	}
        return dto;
    }

    public static List<SectorPriceDTO> sectorpricesToDtos(Collection<SectorPrice> sectorprices) {

        List<SectorPriceDTO> retVal = new ArrayList<>();
        for(SectorPrice sectorPrice : sectorprices) {
        	System.out.println(sectorPrice.getSector());
            retVal.add(SectorPriceDTOConverter.sectorpriceToDto(sectorPrice));
        }

        return retVal;
    }
    
    public static Set<SectorPrice> convertDTOsToSectorPrices(List<SectorPriceDTO> dtos, Event event) {
    	Set<SectorPrice> retVal = new HashSet<SectorPrice>();
    	
    	for (SectorPriceDTO dto : dtos) {
    		retVal.add(SectorPriceDTOConverter.dtoToSectorPrice(dto));
		}
    	
    	return retVal;
    }

}
