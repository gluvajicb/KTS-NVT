package tim20.KTS_NVT.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tim20.KTS_NVT.model.Ticket;
import tim20.KTS_NVT.repository.TicketRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAll() {

        List<Ticket> tickets = ticketRepository.findAll();

        return tickets;
    }


    public Ticket findOne(Long id) {

        Optional<Ticket> ticket = ticketRepository.findById(id);

        if(ticket.isPresent()) {
            return ticket.get();
        }

        return null;
    }


    public Ticket saveTicket(Ticket ticket) {

        Ticket t = (Ticket)ticketRepository.save(ticket);

        return t;
    }

    public void deleteTicket(Long ticketId) {
        ticketRepository.deleteById(ticketId);
    }

}
