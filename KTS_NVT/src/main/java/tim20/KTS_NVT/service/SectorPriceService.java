package tim20.KTS_NVT.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim20.KTS_NVT.model.SectorPrice;
import tim20.KTS_NVT.repository.SectorPriceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SectorPriceService {

    @Autowired
    private SectorPriceRepository sectorPriceRepository;

    public List<SectorPrice> findAll() {

        List<SectorPrice> sectorPrices = sectorPriceRepository.findAll();

        return sectorPrices;
    }


    public SectorPrice findOne(Long id) {

        Optional<SectorPrice> sectorprice = sectorPriceRepository.findById(id);

        if(sectorprice.isPresent()) {
            return sectorprice.get();
        }

        return null;
    }

    public SectorPrice saveSectorPrice(SectorPrice sectorprice) {

        SectorPrice sp = sectorPriceRepository.save(sectorprice);

        return sp;
    }
}
