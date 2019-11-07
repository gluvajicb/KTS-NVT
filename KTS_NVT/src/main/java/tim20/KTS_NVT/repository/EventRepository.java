package tim20.KTS_NVT.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import tim20.KTS_NVT.model.Event;

public interface EventRepository extends JpaRepository<Event, Long>
{

}
