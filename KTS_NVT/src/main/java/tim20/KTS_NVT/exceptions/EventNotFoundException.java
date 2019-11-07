package tim20.KTS_NVT.exceptions;

public class EventNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private long eventId;

    public EventNotFoundException(long eventId) {
        this.eventId = eventId;
    }

    public long getEventId() {
        return eventId;
    }

}
