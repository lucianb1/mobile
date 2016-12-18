package ro.hoptrop.model.timetable;

/**
 * Created by Luci on 17-Dec-16.
 */
public class WeekTimetableBuilder {

    private int id;
    private int memberID;
    private short[][] weekTimtable;

    WeekTimetableBuilder() {
    }

    public WeekTimetable build() {
        return new WeekTimetable(id, memberID, weekTimtable);
    }

    public WeekTimetableBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public WeekTimetableBuilder setMemberID(int memberID) {
        this.memberID = memberID;
        return this;
    }

    public WeekTimetableBuilder setWeekTimetable(short[][] weekTimtable) {
        this.weekTimtable = weekTimtable;
        return this;
    }
}
