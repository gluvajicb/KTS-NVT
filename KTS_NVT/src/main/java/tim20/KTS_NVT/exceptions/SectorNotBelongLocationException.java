package tim20.KTS_NVT.exceptions;

public class SectorNotBelongLocationException extends RuntimeException {

	private long sectorId;
	private long locationId;
	private long eventId;

	public SectorNotBelongLocationException() {
	}

	public SectorNotBelongLocationException(long sectorId, long locationId, long eventId) {
		this.sectorId = sectorId;
		this.locationId = locationId;
		this.eventId = eventId;
	}

	public long getSectorId() {
		return sectorId;
	}

	public long getLocationId() {
		return locationId;
	}

	public long getEventId() {
		return eventId;
	}

}
