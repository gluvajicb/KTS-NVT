package tim20.KTS_NVT.dto;

import tim20.KTS_NVT.model.EventCategory;
import tim20.KTS_NVT.model.EventDay;
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
    private List<EventDayDTO> days;
    private Boolean isActive;
    private String eventCategory;
    private Integer max_tickets;
    private Long locationID;
    private List<SectorPriceDTO> prices;
    private boolean enabledDeactivation;

    public EventDTO() {}

    public EventDTO(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }
    
    public List<SectorPriceDTO> getPrices() {
		return prices;
	}

	public void setPrices(List<SectorPriceDTO> prices) {
		this.prices = prices;
	}

	public Long getId() {
        return id;
    }

	
    public boolean isEnabledDeactivation() {
		return enabledDeactivation;
	}

	public void setEnabledDeactivation(boolean enabledDeactivation) {
		this.enabledDeactivation = enabledDeactivation;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(String eventCategory) {
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

	public List<EventDayDTO> getDays() {
		return days;
	}

	public void setDays(List<EventDayDTO> days) {
		this.days = days;
	}
	
	
}
