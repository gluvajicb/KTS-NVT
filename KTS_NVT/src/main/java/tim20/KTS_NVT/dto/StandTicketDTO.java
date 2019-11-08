package tim20.KTS_NVT.dto;

public class StandTicketDTO {
    private Long eventID;
    private Long eventDayID;
    private boolean isSingleDay;
    private double price;
    private Long sectorID;


    public StandTicketDTO(Long eventID, Long eventDayID, boolean isSingleDay, double price, Long sectorID) {
        this.eventID = eventID;
        this.eventDayID = eventDayID;
        this.isSingleDay = isSingleDay;
        this.price = price;
        this.sectorID = sectorID;
    }

    public Long getSectorID() {
        return sectorID;
    }

    public void setSectorID(Long sectorID) {
        this.sectorID = sectorID;
    }

    public Long getEventID() {
        return eventID;
    }

    public void setEventID(Long eventID) {
        this.eventID = eventID;
    }

    public Long getEventDayID() {
        return eventDayID;
    }

    public void setEventDayID(Long eventDayID) {
        this.eventDayID = eventDayID;
    }

    public boolean isSingleDay() {
        return isSingleDay;
    }

    public void setSingleDay(boolean singleDay) {
        isSingleDay = singleDay;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
