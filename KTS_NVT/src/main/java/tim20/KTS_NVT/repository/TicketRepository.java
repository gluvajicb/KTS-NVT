package tim20.KTS_NVT.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tim20.KTS_NVT.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
