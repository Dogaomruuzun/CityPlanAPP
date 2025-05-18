package iterator;

import model.City;

public class SunnyCityIterator extends WeatherBasedIterator {
    @Override
    protected boolean match(City city) {
        return city.getCurrentWeatherState().equalsIgnoreCase("SUNNY");
    }
}
