package objects.OpenWeather;


/**
 * Created by alvaro.montes on 01/03/2017.
 */
public class Coords {
    private float lon; // City geo location, longitude
    private float lat; // City geo location, latitude

    public Coords(float lat, float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }
}
