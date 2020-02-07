package tim20.KTS_NVT.exceptions;

public class SectorCanNotBeDeletedException extends RuntimeException{
	
	public SectorCanNotBeDeletedException(long sectorId) {
		this.sectorId = sectorId;
	}

	long sectorId;

	public long getSectorId() {
		return sectorId;
	}

	public void setSectorId(long sectorId) {
		this.sectorId = sectorId;
	}

}
