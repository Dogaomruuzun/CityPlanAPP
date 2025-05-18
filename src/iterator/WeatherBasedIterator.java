package iterator;

import model.City;
import repository.CityRepository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class WeatherBasedIterator implements CityIterator {
    protected List<City> filteredCities;
    protected Iterator<City> iterator;

    public WeatherBasedIterator() {
        filteredCities = new ArrayList<>();
        for (City city : CityRepository.getInstance().getCities()) {
            if (match(city)) {
                filteredCities.add(city);
            }
        }
        iterator = filteredCities.iterator();
    }

    protected abstract boolean match(City city);

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public City next() {
        return iterator.next();
    }
}
