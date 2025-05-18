package decorator;

public abstract class CityDecorator implements CityComponent {
    protected CityComponent decoratedCity;

    public CityDecorator(CityComponent decoratedCity) {
        this.decoratedCity = decoratedCity;
    }

    @Override
    public String getDescription() {
        return decoratedCity.getDescription();
    }
}
