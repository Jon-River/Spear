package objects.OpenWeather;




public class WeatherResponse {

    private Coords coords;

    private Weather weather;

    private String base; // May be null, Internal parameter

    private Main main;

    private Wind wind;

    private float clouds; // Cloudiness, %

    private long dt; // Time of data calculation, unix, UTC

    private Sys sys;

    private long idCity; // City ID

    private String name; // City name

    private long cod; // Internal parameter

    public WeatherResponse(){
        super();
    }

    public WeatherResponse(Coords coords, Weather weather, String base, Main main, Wind wind, float clouds, long dt, Sys sys, long idCity, String name, long cod) {
        this.coords = coords;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.wind = wind;
        this.clouds = clouds;

        this.dt = dt;
        this.sys = sys;
        this.idCity = idCity;
        this.name = name;
        this.cod = cod;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public float getClouds() {
        return clouds;
    }

    public void setClouds(float clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public long getIdCity() {
        return idCity;
    }

    public void setIdCity(long idCity) {
        this.idCity = idCity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCod() {
        return cod;
    }

    public void setCod(long cod) {
        this.cod = cod;
    }
}