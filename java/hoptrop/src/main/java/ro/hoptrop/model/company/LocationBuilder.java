package ro.hoptrop.model.company;

/**
 * Created by Luci on 12-Dec-16.
 */
public class LocationBuilder {

    private String address;

    private Double latitude;
    private Double longitude;

    LocationBuilder() {
    }

    public LocationBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public LocationBuilder setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public LocationBuilder setLongitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }
}
