package tim20.KTS_NVT.exceptions;

public class EventDayNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    private long dayId;

    public EventDayNotFoundException(long dayId) {
        this.dayId = dayId;
    }

    public long getEventDayId() {
        return dayId;
    }
}
