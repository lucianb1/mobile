package ro.hoptrop.model.timetable;

import java.util.Date;

/**
 * Created by Luci on 17-Dec-16.
 * Every index of hours array, represents 15 mins
 */
public class Timetable {

    private final int id;
    private final int memberID;
    private final Date date;
    private final int[] hours;

    Timetable(int id, int memberID, Date date, int[] hours) {
        this.id = id;
        this.memberID = memberID;
        this.date = date;
        this.hours = hours;
    }

    public static TimetableBuilder builder() {
        return new TimetableBuilder();
    }

    public int getId() {
        return id;
    }

    public int getMemberID() {
        return memberID;
    }

    public Date getDate() {
        return date;
    }

    public int[] getHours() {
        return hours;
    }
}
