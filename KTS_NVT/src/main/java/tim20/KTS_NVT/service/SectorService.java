package tim20.KTS_NVT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.repository.SectorRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SectorService {
	
	@Autowired
	private SectorRepository sectorRepository;

	public List<Sector> findAll() {

		List<Sector> sectors = sectorRepository.findAll();

		return sectors;
	}

	public Sector findOne(Long id) {

		Optional<Sector> sectorprice = sectorRepository.findById(id);

		if (sectorprice.isPresent()) {
			return sectorprice.get();
		}

		return null;
	}

	public Sector saveSector(Sector sectorprice) {

		Sector sp = sectorRepository.save(sectorprice);

		return sp;
	}
}
