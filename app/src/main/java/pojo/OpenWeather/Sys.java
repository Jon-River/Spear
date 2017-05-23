package pojo.OpenWeather;


/**
 * Created by alvaro.montes on 01/03/2017.
 */
public class Sys {
    private int type; // Internal parameter
    private long id; // Internal parameter
    private float message; // Internal parameter
    private String country; // Country code (SP, JP etc.)
    private long sunset; // Sunrise time, unix, UTC
    private long sunrise; // Sunset  time, unix, UTC

    public Sys(int type, long id, float message, String country, long sunset, long sunrise) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        this.sunset = sunset;
        this.sunrise = sunrise;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }
}
