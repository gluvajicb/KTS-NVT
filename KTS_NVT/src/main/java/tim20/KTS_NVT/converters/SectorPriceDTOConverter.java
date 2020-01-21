package tim20.KTS_NVT.converters;

import tim20.KTS_NVT.dto.SectorPriceDTO;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.service.SectorService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        SectorPriceDTO dto = new SectorPriceDTO(sectorprice.getId(), sectorprice.getPrice(), sectorprice.getSector().getId(), sectorprice.getEvent().getId());

        return dto;
    }

    public static List<SectorPriceDTO> sectorpricesToDtos(Collection<SectorPrice> sectorprices) {

        List<SectorPriceDTO> retVal = new ArrayList<>();

        for(SectorPrice sectorPrice : sectorprices) {
            retVal.add(SectorPriceDTOConverter.sectorpriceToDto(sectorPrice));
        }

        return retVal;
    }

}
