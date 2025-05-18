package strategy;

import model.City;
import java.util.List;

public interface CitySortStrategy {
    List<City> sort(List<City> cities);
}