package iterator;

import model.City;

public class SnowyCityIterator extends WeatherBasedIterator {
    @Override
    protected boolean match(City city) {
        return city.getCurrentWeatherState().equalsIgnoreCase("SNOWY");
    }
}
