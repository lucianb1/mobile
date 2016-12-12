package ro.hoptrop.utils;

import ro.hoptrop.model.company.Location;

/**
 * Created by Luci on 13-Dec-16.
 */
public class SqlUtils {

    public static final String OPEN_BRACKET = "(";
    public static final String CLOSED_BRACKET = ")";
    public static final String COMMA = ",";

    public static String extractCoordinatesInSqlFormat(Location location) {
        return "GeomFromText('" + toPoint(location.getLongitude(), location.getLatitude()) + "')";
    }

    private static String toPoint(Double longitude, Double latitude) {
        return String.format("POINT(%d %d)", longitude, latitude);
    }

}
