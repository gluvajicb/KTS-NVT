package tim20.KTS_NVT.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import tim20.KTS_NVT.converters.SectorPriceDTOConverter;
import tim20.KTS_NVT.dto.SectorPriceDTO;
import tim20.KTS_NVT.exceptions.SectorPriceNotFoundException;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.model.Error;
import tim20.KTS_NVT.service.EventService;
import tim20.KTS_NVT.service.SectorPriceService;
import tim20.KTS_NVT.service.SectorService;

import java.util.Collection;


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

}
