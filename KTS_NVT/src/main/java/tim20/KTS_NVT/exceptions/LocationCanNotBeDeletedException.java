package tim20.KTS_NVT.exceptions;

public class LocationCanNotBeDeletedException extends RuntimeException {

	private long locationId;

	public LocationCanNotBeDeletedException(long locationId) {
		super();
		this.locationId = locationId;
	}

	public long getLocationId() {
		return locationId;
	}

	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}

}
