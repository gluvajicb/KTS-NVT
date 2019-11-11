package tim20.KTS_NVT.dto;

public class SectorDTO {

	private Long id;
	
	private String title;
	private String type;
	private int max_guests;
	private int row_num;
	private int column_num;

	public SectorDTO() {
		this.max_guests = -1;
		this.column_num = -1;
		this.row_num = -1;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMax_guests() {
		return max_guests;
	}

	public void setMax_guests(int max_guests) {
		this.max_guests = max_guests;
	}

	public int getRow_num() {
		return row_num;
	}

	public void setRow_num(int row_num) {
		this.row_num = row_num;
	}

	public int getColumn_num() {
		return column_num;
	}

	public void setColumn_num(int column_num) {
		this.column_num = column_num;
	}

}
