package ro.hoptrop.web.response.timetable;

import java.util.Date;

/**
 * Created by Luci on 08-Jan-17.
 */
public class DayTimetableJsonResponse {

    private Date date;
    private String timetable;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }
}
