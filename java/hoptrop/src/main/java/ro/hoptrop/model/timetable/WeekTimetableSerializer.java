package ro.hoptrop.model.timetable;

import java.nio.ByteBuffer;
import java.nio.ShortBuffer;

/**
 * Created by Luci on 18-Dec-16.
 */
public class WeekTimetableSerializer {

    private static final int ROWS = 7;
    private static final int COLUMNS = 4 * 24;

    public static byte[] serialize(short[][] timetable) {
        ByteBuffer buffer = ByteBuffer.allocate(ROWS * COLUMNS * 2);
        ShortBuffer shortBuffer = buffer.asShortBuffer();
        for (int i = 0; i < ROWS; i++) {
            shortBuffer.put(timetable[i]);
        }
        return buffer.array();
    }

    public static short[][] deserialize(byte[] byteArray) {
        int bytesCount = ROWS * COLUMNS * 2;
        if (byteArray.length != bytesCount) {
            throw new RuntimeException("Unsupported format");
        }
        short[][] result = new short[ROWS][COLUMNS];
        for (int i = 0; i < ROWS; i++) {
            ByteBuffer buffer = ByteBuffer.wrap(byteArray, i * COLUMNS * 2, COLUMNS * 2);
            ShortBuffer shortBuffer = buffer.asShortBuffer();
            shortBuffer.get(result[i]);
        }
        return result;
    }

}
