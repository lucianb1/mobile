package ro.hoptrop.model.timetable;

/**
 * Created by Luci on 17-Dec-16.
 */
public class TimeIntervalSerializer {

    private static final int INTERVAL_SIZE = 4; // in bytes = 2 x short

    public static byte[] serialize(TimeInterval[] array) {
        int size = array.length * INTERVAL_SIZE;
        byte[] result = new byte[size];
        for (int i = 0; i < size; i++) {
            result[4 * i] = (byte) (array[i].getA() & 0xff);
            result[4 * i + 1] = (byte) ((array[i].getA() >> 8) & 0xff);
            result[4 * i + 2] = (byte) (array[i].getB() & 0xff);
            result[4 * i + 3] = (byte) ((array[i].getB() >> 8) & 0xff);
        }
        return result;
    }

    public static TimeInterval[] deserialize(byte[] bytes) {
        int size = bytes.length / INTERVAL_SIZE;
        TimeInterval[] array = new TimeInterval[size];
        for (int i = 0; i < size; i++) {
            short a = (short) ((bytes[4 * i] & 0xFF) | bytes[4 * i + 1] << 8);
            short b = (short) ((bytes[4 * i + 2] & 0xFF) | bytes[4 * i + 3] << 8);
            array[i] = new TimeInterval(a, b);
        }
        return array;
    }


}
