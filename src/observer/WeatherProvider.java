package observer;

import model.City;
import repository.CityRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WeatherProvider extends Thread implements Subject {

    private final List<Observer> observers = new ArrayList<>();
    private final List<City> cities = CityRepository.getInstance().getCities();
    private final String[] weatherStates = { "SUNNY", "CLOUDY", "RAINY", "SNOWY" };
    private final Random random = new Random();

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                break;
            }

            for (City city : cities) {
                double newTemp = 10 + random.nextInt(25);
                String newWeather = weatherStates[random.nextInt(weatherStates.length)];
                city.setCurrentTemperature(newTemp);
                city.setCurrentWeatherState(newWeather);
            }

            notifyObservers();
        }
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }  //Adds a new observer to the system

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    } //Removes a previously added observer from the system.

    @Override
    public void notifyObservers() {
        for (Observer o : observers) {
            o.update();
        }
    }
}
