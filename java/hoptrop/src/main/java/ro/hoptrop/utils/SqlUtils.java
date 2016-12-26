package ro.hoptrop.utils;

/**
 * Created by Luci on 13-Dec-16.
 */
public class SqlUtils {

    public static String createPointFromLocation(Double longitude, Double latitude) {
        return String.format("POINT(%f %f)", longitude, latitude);
    }

}
