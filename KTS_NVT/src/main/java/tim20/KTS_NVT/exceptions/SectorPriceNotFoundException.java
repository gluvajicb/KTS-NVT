package tim20.KTS_NVT.exceptions;

public class SectorPriceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private long sectorPriceId;

    public SectorPriceNotFoundException(long sectorPriceId) {
        this.sectorPriceId = sectorPriceId;
    }

    public long getSectorPriceId() {
        return sectorPriceId;
    }
}
