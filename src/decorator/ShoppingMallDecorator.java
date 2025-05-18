package decorator;

public class ShoppingMallDecorator extends CityDecorator {

    public ShoppingMallDecorator(CityComponent decoratedCity) {
        super(decoratedCity);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " 🛍️ Shopping Mall";
    }
}
