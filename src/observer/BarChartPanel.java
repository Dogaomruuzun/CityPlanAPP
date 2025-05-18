package observer;

import model.City;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import repository.CityRepository;

import javax.swing.*;
import java.util.List;

public class BarChartPanel extends JPanel implements Observer {

    private DefaultCategoryDataset dataset;
    private List<City> cities;

    public BarChartPanel() {
        cities = CityRepository.getInstance().getCities();
        dataset = new DefaultCategoryDataset();
        updateData(); // ilk veri yüklemesi

        JFreeChart chart = ChartFactory.createBarChart(
                "City Temperatures",
                "City",
                "°C",
                dataset
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        this.setLayout(new java.awt.BorderLayout());
        this.add(chartPanel, java.awt.BorderLayout.CENTER);
    }

    @Override
    public void update() {
        updateData(); // yeni verilerle dataset güncelleniyor
    }

    private void updateData() {
        dataset.clear();
        for (City city : cities) {
            dataset.addValue(city.getCurrentTemperature(), "Temperature", city.getName());
        }
    }
}
