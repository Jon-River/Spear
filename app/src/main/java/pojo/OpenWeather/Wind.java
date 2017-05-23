package objects.OpenWeather;


/**
 * Created by alvaro.montes on 01/03/2017.
 */
public class Wind {
    private float speed; // Wind speed. Unit Default: meter/sec, Metric: meter/sec, Imperial: miles/hour.
    private int deg; // Wind direction, degrees (meteorological)

    public Wind(float speed, int deg) {
        this.speed = speed;
        this.deg = deg;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public int getDeg() {
        return deg;
    }

    public void setDeg(int deg) {
        this.deg = deg;
    }
}
