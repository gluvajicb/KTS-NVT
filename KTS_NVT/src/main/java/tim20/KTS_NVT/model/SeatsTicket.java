package tim20.KTS_NVT.model;

public class SeatsTicket extends Ticket {

	private Integer rowNumber;
	private Integer columnNumber;

	public SeatsTicket() {
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}

	public Integer getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(Integer columnNumber) {
		this.columnNumber = columnNumber;
	}

}