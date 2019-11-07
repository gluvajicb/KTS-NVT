package tim20.KTS_NVT.dto;

import tim20.KTS_NVT.model.EventCategory;
import tim20.KTS_NVT.model.Location;
import tim20.KTS_NVT.model.SectorPrice;

import java.sql.Date;
import java.util.List;
import java.util.Set;

public class EventDTO
{
    private Long id;
    private String title;
    private String description;
    private List<Date> dates;
    private Boolean isActive;
    private EventCategory eventCategory;
    private Long locationID;
    private Set<Long> sectorpriceIDs;
    private Set<Long> ticketIDs;

    public EventDTO() {}

    public EventDTO(Long id, String title, String description, List<Date> dates, Boolean isActive, EventCategory eventCategory, Long locationID, Set<Long> sectorpriceIDs, Set<Long> ticketIDs) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dates = dates;
        this.isActive = true;
        this.eventCategory = eventCategory;
        this.locationID = locationID;
        this.sectorpriceIDs = sectorpriceIDs;
        this.ticketIDs = ticketIDs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Date> getDates() {
        return dates;
    }

    public void setDates(List<Date> dates) {
        this.dates = dates;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public Long getLocationID() {
        return locationID;
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

    public Set<Long> getSectorpriceIDs() {
        return sectorpriceIDs;
    }

    public void setSectorpriceIDs(Set<Long> sectorpriceIDs) {
        this.sectorpriceIDs = sectorpriceIDs;
    }

    public Set<Long> getTicketIDs() {
        return ticketIDs;
    }

    public void setTicketIDs(Set<Long> ticketIDs) {
        this.ticketIDs = ticketIDs;
    }
}
