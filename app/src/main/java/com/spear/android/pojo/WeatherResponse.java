package com.spear.android.pojo;


public class WeatherResponse {
    String description, name, icon;
    double temperature, humidity, tempMax, tempMin;
    long pressure, sunrise, sunset;
    float speed, deg;


    public WeatherResponse() {
    }

    public WeatherResponse(String description, double temperature, long pressure, double humidity, double tempMax, double tempMin, float speed, float deg, long sunrise, long sunset, String name, String icon) {
        this.description = description;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
        this.speed = speed;
        this.deg = deg;
        this.sunrise = sunrise;
        this.sunset = sunset;
        this.name = name;
        this.icon = icon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public long getPressure() {
        return pressure;
    }

    public void setPressure(long pressure) {
        this.pressure = pressure;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getDeg() {
        return deg;
    }

    public void setDeg(float deg) {
        this.deg = deg;
    }
}