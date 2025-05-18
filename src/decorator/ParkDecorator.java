package decorator;

public class ParkDecorator extends CityDecorator {

    public ParkDecorator(CityComponent decoratedCity) {
        super(decoratedCity);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " 🌳 Park";
    }
}
