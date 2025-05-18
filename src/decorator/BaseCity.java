package decorator;

import model.City;

public class BaseCity implements CityComponent {

    private final City city;

    public BaseCity(City city) {
        this.city = city;
    }

    @Override
    public String getDescription() {
        return city.getName();
    }

    public City getCity() {
        return city;
    }
}
