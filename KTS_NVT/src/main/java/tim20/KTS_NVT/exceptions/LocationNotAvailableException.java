package tim20.KTS_NVT.exceptions;

import java.util.Date;

public class LocationNotAvailableException extends RuntimeException {

	private long locationId;
	private Date eventDate;
	
	public LocationNotAvailableException(long locationId, Date eventDate) {
		this.locationId = locationId;
		this.eventDate = eventDate;
	}
	
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
}
