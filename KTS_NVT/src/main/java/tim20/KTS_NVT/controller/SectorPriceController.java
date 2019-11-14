package tim20.KTS_NVT.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import tim20.KTS_NVT.converters.SectorPriceDTOConverter;
import tim20.KTS_NVT.dto.SectorPriceDTO;
import tim20.KTS_NVT.exceptions.EventNotFoundException;
import tim20.KTS_NVT.exceptions.SectorNotBelongLocationException;
import tim20.KTS_NVT.exceptions.SectorNotFoundException;
import tim20.KTS_NVT.exceptions.SectorPriceNotFoundException;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.model.Event;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.service.SectorService;


@Controller
@RequestMapping(value = "/sectorprices")
public class SectorPriceController
{
    @Autowired
    private SectorPriceService sectorPriceService;

    @Autowired
    private EventService eventService;

    @Autowired
    private SectorService sectorService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<SectorPrice>> getAll() {

        Collection<SectorPrice> sectorprices = sectorPriceService.findAll();

        return new ResponseEntity<Collection<SectorPrice>>(sectorprices, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectorPrice> addSectorPrice(@RequestBody SectorPriceDTO dto)
    {
        SectorPrice sectorPrice = SectorPriceDTOConverter.dtoToSectorPrice(dto);
    	
    	Event event = eventService.findOne(dto.getEventID());
    	

        if (event == null) {
            throw new EventNotFoundException(dto.getEventID());
        }
        
        
        Sector sector = sectorService.findOne(dto.getSectorID());

        if (sector == null) {
            throw new SectorNotFoundException(dto.getSectorID());
        }
        
        //da li sektor pripada lokaciji?
        
        boolean found = false;
        
        for (Sector s : event.getLocation().getSectors()) {
        	if(s.getId().longValue() == sector.getId().longValue()) {
        		found = true;
        		break;
        	}
		}
        
        if(found == false) {
        	throw new SectorNotBelongLocationException(sector.getId(), event.getLocation().getId(), event.getId());
        }

        sectorPrice.setEvent(event);
        sectorPrice.setSector(sector);
        
        sectorPrice = sectorPriceService.saveSectorPrice(sectorPrice);

        return new ResponseEntity<SectorPrice>(sectorPrice, HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SectorPrice> updateSectorPrice(@PathVariable("id") Long id, @RequestBody SectorPriceDTO dto)
    {
        SectorPrice sectorprice = SectorPriceDTOConverter.dtoToSectorPrice(dto);

        sectorprice = sectorPriceService.updateSectorPrice(sectorprice);

        if(sectorprice == null) {
            throw new SectorPriceNotFoundException(id);
        } else {
            return new ResponseEntity<SectorPrice>(sectorprice, HttpStatus.OK);
        }
    }


    /* --------------    Exception Handler    --------------- */

    @ExceptionHandler(SectorPriceNotFoundException.class)
    public ResponseEntity<Error> sectorPriceNotFound(SectorPriceNotFoundException e) {
        long sectorpriceId = e.getSectorPriceId();
        Error error = new Error(1, "Location [" + sectorpriceId + "] not found");
        return new ResponseEntity<Error>(error, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(SectorNotBelongLocationException.class)
    public ResponseEntity<Error> sectorNotBelongLocation(SectorNotBelongLocationException e) {
        long sectorId = e.getSectorId();
        long locationId = e.getLocationId();
        long eventId = e.getEventId();
        Error error = new Error(1, "Sector [" + sectorId + "] does not belong to Location [" + locationId + "] for Event [" + eventId + "]");
        return new ResponseEntity<Error>(error, HttpStatus.EXPECTATION_FAILED);
    }

}
