package decorator;

public class CityCenterDecorator extends CityDecorator {

    public CityCenterDecorator(CityComponent decoratedCity) {
        super(decoratedCity);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " 🏙️ City Center";
    }
}
