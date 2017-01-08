package ro.hoptrop.web.response.timetable;

import java.util.Date;

/**
 * Created by Luci on 08-Jan-17.
 */
public class DayTimetableJsonResponse {

    private Date date;
    private short[] timetable;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public short[] getTimetable() {
        return timetable;
    }

    public void setTimetable(short[] timetable) {
        this.timetable = timetable;
    }
}
