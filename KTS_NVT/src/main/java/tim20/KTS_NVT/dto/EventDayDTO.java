package tim20.KTS_NVT.dto;

//import java.util.Date;
import java.sql.Date;

public class EventDayDTO
{

    private Long id;
    private String title;
    private Date eventdate;
    private Long eventId;

    public EventDayDTO() {}

    public EventDayDTO(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public EventDayDTO(Long id, String title, Date eventdate, Long eventId) {
        this.id = id;
        this.title = title;
        this.eventdate = eventdate;
        this.eventId = eventId;
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

    public Date getEventdate() {
        return eventdate;
    }

    public void setEventdate(Date eventdate) {
        this.eventdate = eventdate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }
}
