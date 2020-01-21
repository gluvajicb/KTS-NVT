package tim20.KTS_NVT.model;
import javax.persistence.Entity;

@Entity(name="seats_ticket")
public class SeatsTicket extends Ticket {

	private Integer rowNum;
	private Integer columnNum;

	public SeatsTicket() {
	}

	


	public SeatsTicket(EventDay day, Boolean singleDay, Double price, Event event, Sector sector,
			Integer rowNum, Integer columnNum) {
		super( day, singleDay, price, event, sector);
		this.rowNum = rowNum;
		this.columnNum = columnNum;
	}




	public Integer getRowNum() {
		return rowNum;
	}

	public void setRowNum(Integer rowNum) {
		this.rowNum = rowNum;
	}

	public Integer getColumnNum() {
		return columnNum;
	}

	public void setColumnNum(Integer columnNum) {
		this.columnNum = columnNum;
	}


}