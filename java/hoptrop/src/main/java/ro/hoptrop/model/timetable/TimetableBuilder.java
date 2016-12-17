package ro.hoptrop.model.timetable;

import java.util.Date;

/**
 * Created by Luci on 17-Dec-16.
 */
public class TimetableBuilder {

    private int id;
    private int memberID;
    private Date date;
    private int[] hours;

    TimetableBuilder() {
    }

    public Timetable build() {
        return new Timetable(id, memberID, date, hours);
    }

    public TimetableBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TimetableBuilder setMemberID(int memberID) {
        this.memberID = memberID;
        return this;
    }

    public TimetableBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public TimetableBuilder setHours(int[] hours) {
        this.hours = hours;
        return this;
    }
}
