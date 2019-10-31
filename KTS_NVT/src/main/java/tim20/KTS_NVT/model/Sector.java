package tim20.KTS_NVT.model;

public abstract class Sector {

	private Long id;
	private String title;

	public Sector() {
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

}