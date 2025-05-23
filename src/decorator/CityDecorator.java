package decorator;

public abstract class CityDecorator implements CityComponent { //wrap
    protected CityComponent decoratedCity;

    public CityDecorator(CityComponent decoratedCity) {
        this.decoratedCity = decoratedCity;
    }

    @Override
    public String getDescription() {
        return decoratedCity.getDescription();
    }
}
