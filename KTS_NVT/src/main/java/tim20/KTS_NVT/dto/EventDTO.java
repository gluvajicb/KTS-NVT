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
    private Integer max_tickets;
    private Long locationID;

    public EventDTO() {}

    public EventDTO(Long id, String title, String description, List<Date> dates, Boolean isActive, EventCategory eventCategory, Integer max_tickets, Long locationID) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dates = dates;
        this.isActive = true;
        this.eventCategory = eventCategory;
        this.max_tickets = max_tickets;
        this.locationID = locationID;
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

    public Integer getMax_tickets() {
        return max_tickets;
    }

    public void setMax_tickets(Integer max_tickets) {
        this.max_tickets = max_tickets;
    }

    public Long getLocationID() {
        return locationID;
    }

    public void setLocationID(Long locationID) {
        this.locationID = locationID;
    }

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}
}
