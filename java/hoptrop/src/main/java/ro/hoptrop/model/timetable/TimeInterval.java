package ro.hoptrop.model.timetable;

/**
 * Every unit means 15 mins
 */
public class TimeInterval {

    private final short a;
    private final short b;

    public TimeInterval(short a, short b) {
        this.a = a;
        this.b = b;
    }

    public short getA() {
        return a;
    }

    public short getB() {
        return b;
    }
}
