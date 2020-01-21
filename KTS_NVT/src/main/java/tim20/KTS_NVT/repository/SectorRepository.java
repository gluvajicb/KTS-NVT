package tim20.KTS_NVT.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tim20.KTS_NVT.model.Sector;
import tim20.KTS_NVT.model.StandSector;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long>{

	List<Sector> findByLocationId(Long location_id);

}
