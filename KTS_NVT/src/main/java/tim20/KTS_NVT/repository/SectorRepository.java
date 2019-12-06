package tim20.KTS_NVT.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tim20.KTS_NVT.model.Sector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long>{

	List<Sector> findByLocationId(Long location_id);
}
