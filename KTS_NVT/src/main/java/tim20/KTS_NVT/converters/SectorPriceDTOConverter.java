package tim20.KTS_NVT.converters;

import tim20.KTS_NVT.dto.SectorPriceDTO;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.service.SectorService;

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

}
