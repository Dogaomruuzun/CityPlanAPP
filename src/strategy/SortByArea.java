package strategy;

import model.City;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class SortByArea implements CitySortStrategy {
    @Override
    public List<City> sort(List<City> cities) {
        return cities.stream()
                .sorted(Comparator.comparing(City::getArea)) // Küçükten büyüğe
                .collect(Collectors.toList());
    }
}
