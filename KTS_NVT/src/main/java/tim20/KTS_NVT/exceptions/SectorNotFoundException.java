package tim20.KTS_NVT.exceptions;

public class SectorNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private Long sectorId;

	public SectorNotFoundException(Long sectorId) {
		this.sectorId = sectorId;
	}

	public Long getSectorId() {
		return sectorId;
	}

}
