package ro.hoptrop.model.timetable;

import java.util.Date;

/**
 * Created by Luci on 08-Jan-17.
 */
public class DayTimetableBuilder {

    private int id;
    private Date date;
    private short[] timetable;

    public DayTimetable build() {
        return new DayTimetable(id, date, timetable);
    }

    public DayTimetableBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public DayTimetableBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public DayTimetableBuilder setTimetable(short[] timetable) {
        this.timetable = timetable;
        return this;
    }
}
