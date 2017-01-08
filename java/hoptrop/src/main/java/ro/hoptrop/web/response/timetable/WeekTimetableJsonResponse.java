package ro.hoptrop.web.response.timetable;

/**
 * Created by Luci on 08-Jan-17.
 */
public class WeekTimetableJsonResponse {

    private short[][] timetable;

    public short[][] getTimetable() {
        return timetable;
    }

    public void setTimetable(short[][] timetable) {
        this.timetable = timetable;
    }
}
