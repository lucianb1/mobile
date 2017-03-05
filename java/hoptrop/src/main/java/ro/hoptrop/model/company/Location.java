package ro.hoptrop.model.company;

import org.hibernate.validator.constraints.NotBlank;

import java.util.Objects;

/**
 * Created by Luci on 12-Dec-16.
 */
public class Location {

    @NotBlank
    private final String address;

    private final Double latitude;
    private final Double longitude;

    Location(String address, Double longitude, Double latitude) {
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

    public boolean equals(Object o) {
        if (o instanceof Location) {
            Location otherLocation = (Location) o;
            return this.address.equals(otherLocation.getAddress()) &&
                Objects.equals(latitude, otherLocation.getLatitude()) &&
                Objects.equals(longitude, otherLocation.getLongitude());
        } else {
            return false;
        }
    }
}
