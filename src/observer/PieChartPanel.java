package observer;

import model.City;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import repository.CityRepository;

import javax.swing.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PieChartPanel extends JPanel implements Observer {

    private DefaultPieDataset<String> dataset;
    private List<City> cities;

    public PieChartPanel() {
        cities = CityRepository.getInstance().getCities();
        dataset = new DefaultPieDataset<>();
        updateData(); // ilk veriler

        JFreeChart chart = ChartFactory.createPieChart(
                "Weather Distribution",
                dataset,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        this.setLayout(new java.awt.BorderLayout());
        this.add(chartPanel, java.awt.BorderLayout.CENTER);
    }

    @Override
    public void update() {
        updateData(); // veri güncelle
    }

    private void updateData() {
        dataset.clear();
        Map<String, Integer> countMap = new HashMap<>();

        for (City city: cities) {
            String weather = city.getCurrentWeatherState();
            countMap.put(weather, countMap.getOrDefault(weather, 0) + 1);
        }

        for (Map.Entry<String, Integer> entry : countMap.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
    }
}
