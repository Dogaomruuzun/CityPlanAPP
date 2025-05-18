package iterator;

import model.City;

public class CloudyCityIterator extends WeatherBasedIterator {
    @Override
    protected boolean match(City city) {
        return city.getCurrentWeatherState().equalsIgnoreCase("CLOUDY");
    }
}
