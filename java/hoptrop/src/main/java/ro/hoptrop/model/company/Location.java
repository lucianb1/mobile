package ro.hoptrop.model.company;

/**
 * Created by Luci on 12-Dec-16.
 */
public class Location {

    private final String address;

    private final Double latitude;
    private final Double longitude;

    Location(String address, Double latitude, Double longitude) {
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public static LocationBuilder builder() {
        return new LocationBuilder();
    }

    public String getAddress() {
        return address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public boolean hasCoordinates() {
        return latitude != null && longitude != null;
    }
}
