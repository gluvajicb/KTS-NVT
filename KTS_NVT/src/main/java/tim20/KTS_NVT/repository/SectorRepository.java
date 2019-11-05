package tim20.KTS_NVT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tim20.KTS_NVT.model.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long>{

	List<Sector> findByLocationId(Long location_id);
}
