package ro.hoptrop.model.timetable;

import java.util.Date;

/**
 * Created by Luci on 08-Jan-17.
 */
public class DayTimetable {

    private final int id;
    private final Date date;
    private final short[] timetable;

    public DayTimetable(int id, Date date, short[] timetable) {
        this.id = id;
        this.date = date;
        this.timetable = timetable;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public short[] getTimetable() {
        return timetable;
    }
}
