package tim20.KTS_NVT.dto;

public class SectorDTO {

	private Long id;

	private String title;
	private String type;
	private int max_guests;
	private int row_num;
	private int column_num;
	private double top;
	private double left;
	private double height;
	private double width;
	private double angle;

	public SectorDTO() {
		this.max_guests = -1;
		this.column_num = -1;
		this.row_num = -1;
	}



	public SectorDTO(Long id, String title, String type, int max_guests, int row_num, int column_num, double top,
			double left, double height, double width, double angle) {
		super();
		this.id = id;
		this.title = title;
		this.type = type;
		this.max_guests = max_guests;
		this.row_num = row_num;
		this.column_num = column_num;
		this.top = top;
		this.left = left;
		this.height = height;
		this.width = width;
		this.angle = angle;
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



	public double getTop() {
		return top;
	}



	public void setTop(double top) {
		this.top = top;
	}



	public double getLeft() {
		return left;
	}



	public void setLeft(double left) {
		this.left = left;
	}



	public double getHeight() {
		return height;
	}



	public void setHeight(double height) {
		this.height = height;
	}



	public double getWidth() {
		return width;
	}



	public void setWidth(double width) {
		this.width = width;
	}



	public double getAngle() {
		return angle;
	}



	public void setAngle(double angle) {
		this.angle = angle;
	}

}
