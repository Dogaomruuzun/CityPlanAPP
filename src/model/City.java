package model;

public class City {
    private String name;
    private int population;
    private double area;
    private double currentTemperature;
    private String currentWeatherState;

    public City(String name, int population, double area, double currentTemperature, String currentWeatherState) {
        this.name = name;
        this.population = population;
        this.area = area;
        this.currentTemperature = currentTemperature;
        this.currentWeatherState = currentWeatherState;
    }

    public String getName() { return name; }
    public int getPopulation() { return population; }
    public double getArea() { return area; }
    public double getCurrentTemperature() { return currentTemperature; }
    public String getCurrentWeatherState() { return currentWeatherState; }

    public void setCurrentTemperature(double temp) {
        this.currentTemperature = temp;
    }

    public void setCurrentWeatherState(String weather) {
        this.currentWeatherState = weather;
    }

    @Override
    public String toString() {
        return name + " - " + currentWeatherState + " - " + currentTemperature + "°C";
    }
}
