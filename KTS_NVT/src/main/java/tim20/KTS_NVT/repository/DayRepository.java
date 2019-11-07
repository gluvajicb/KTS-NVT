package tim20.KTS_NVT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim20.KTS_NVT.model.EventDay;

public interface DayRepository extends JpaRepository<EventDay, Long> {
}
