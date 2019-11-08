package tim20.KTS_NVT.exceptions;

public class TicketNotFoundException extends RuntimeException{

    private long ticketId;

    public TicketNotFoundException(long eventId) {
        this.ticketId = eventId;
    }

    public long getTicketId() {
        return ticketId;
    }
}
