package iterator;

import model.City;

public class RainyCityIterator extends WeatherBasedIterator {
    @Override
    protected boolean match(City city) {
        return city.getCurrentWeatherState().equalsIgnoreCase("RAINY");
    }
}
