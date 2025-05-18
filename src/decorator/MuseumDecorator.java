package decorator;

public class MuseumDecorator extends CityDecorator {

    public MuseumDecorator(CityComponent decoratedCity) {
        super(decoratedCity);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " 🏛️ Museum";
    }
}

