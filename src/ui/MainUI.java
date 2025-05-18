package ui;

import model.City;
import repository.CityRepository;
import strategy.*;
import iterator.*;

import observer.BarChartPanel;
import observer.PieChartPanel;
import observer.WeatherProvider;
import observer.Observer;

import decorator.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class MainUI extends JFrame implements Observer {

    private JComboBox<String> weatherComboBox;
    private JComboBox<String> sortComboBox;
    private DefaultListModel<String> sortedCityListModel;
    private DefaultListModel<String> filteredCityListModel;
    private JList<String> sortedCityList;
    private JList<String> filteredCityList;
    private BarChartPanel barChartPanel;
    private PieChartPanel pieChartPanel;
    private WeatherProvider weatherProvider;

    private JCheckBox museumBox, parkBox, mallBox, centerBox;
    private JTextArea planSummaryArea;
    private Map<String, CityComponent> decoratedCities = new HashMap<>();

    private String activeWeatherFilter = "All";

    public MainUI() {
        setTitle("City Sorter Application");
        setSize(1400, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));

        String[] sortOptions = { "Sort by Name", "Sort by Population", "Sort by Area" };
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.addActionListener(this::onSortSelected);
        controlPanel.add(new JLabel("Sort Cities:"));
        controlPanel.add(sortComboBox);

        String[] weatherOptions = { "All", "Sunny", "Cloudy", "Rainy", "Snowy" };
        weatherComboBox = new JComboBox<>(weatherOptions);
        weatherComboBox.addActionListener(this::onWeatherSelected);
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Weather Filter:"));
        controlPanel.add(weatherComboBox);

        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(new JLabel("Visit Plan Options:"));
        museumBox = new JCheckBox("Visit Museum");
        parkBox = new JCheckBox("Walk in the Park");
        mallBox = new JCheckBox("Visit Shopping Mall");
        centerBox = new JCheckBox("Visit City Center");
        controlPanel.add(museumBox);
        controlPanel.add(mallBox);
        controlPanel.add(parkBox);
        controlPanel.add(centerBox);

        JButton generatePlanButton = new JButton("Generate Visit Plan");
        generatePlanButton.addActionListener(e -> generatePlan());
        controlPanel.add(Box.createVerticalStrut(10));
        controlPanel.add(generatePlanButton);

        sortedCityListModel = new DefaultListModel<>();
        filteredCityListModel = new DefaultListModel<>();
        sortedCityList = new JList<>(sortedCityListModel);
        filteredCityList = new JList<>(filteredCityListModel);

        JPanel cityListPanel = new JPanel(new GridLayout(1, 2));
        cityListPanel.add(new JScrollPane(sortedCityList));
        cityListPanel.add(new JScrollPane(filteredCityList));

        barChartPanel = new BarChartPanel();
        barChartPanel.setPreferredSize(new Dimension(800, 250));
        pieChartPanel = new PieChartPanel();
        pieChartPanel.setPreferredSize(new Dimension(300, 300));

        planSummaryArea = new JTextArea(4, 50);
        planSummaryArea.setEditable(false);
        planSummaryArea.setBorder(BorderFactory.createTitledBorder("Plan Summary"));
        JScrollPane summaryScrollPane = new JScrollPane(planSummaryArea);

        add(controlPanel, BorderLayout.WEST);
        add(cityListPanel, BorderLayout.CENTER);
        add(barChartPanel, BorderLayout.SOUTH);
        add(pieChartPanel, BorderLayout.EAST);
        add(summaryScrollPane, BorderLayout.NORTH);

        updateCityList(new SortByName());

        weatherProvider = new WeatherProvider();
        weatherProvider.registerObserver(barChartPanel);
        weatherProvider.registerObserver(pieChartPanel);
        weatherProvider.registerObserver(this);
        weatherProvider.start();

        setVisible(true);
    }

    @Override
    public void update() {
        SwingUtilities.invokeLater(this::applyDynamicWeatherFilter);
    }

    private void applyDynamicWeatherFilter() {
        filteredCityListModel.clear();
        Iterator<City> iterator;

        switch (activeWeatherFilter) {
            case "Sunny" -> iterator = new SunnyCityIterator();
            case "Cloudy" -> iterator = new CloudyCityIterator();
            case "Rainy" -> iterator = new RainyCityIterator();
            case "Snowy" -> iterator = new SnowyCityIterator();
            default -> {
                for (City city : CityRepository.getInstance().getCities()) {
                    filteredCityListModel.addElement(formatCityInfo(city));
                }
                return;
            }
        }

        while (iterator.hasNext()) {
            City city = iterator.next();
            filteredCityListModel.addElement(formatCityInfo(city));
        }
    }

    private void onWeatherSelected(ActionEvent e) {
        activeWeatherFilter = (String) weatherComboBox.getSelectedItem();
        applyDynamicWeatherFilter();
    }

    private void generatePlan() {
        String selected = sortedCityList.getSelectedValue(); // SOL listedeki şehir
        if (selected == null) return;
        String cityName = selected.split(" ")[0];
        StringBuilder summary = new StringBuilder("Base plan for visiting " + cityName + ":\n");
        double totalTime = 0;
        double totalCost = 0;
        if (museumBox.isSelected()) {
            summary.append("Visit the Main Museum\n");
            totalTime += 2.0;
            totalCost += 20.0;
        }
        if (centerBox.isSelected()) {
            summary.append("City Center Walk\n");
            totalTime += 1.5;
            totalCost += 0.0;
        }
        if (mallBox.isSelected()) {
            summary.append("Shopping Mall\n");
            totalTime += 2.0;
            totalCost += 30.0;
        }
        if (parkBox.isSelected()) {
            summary.append("Walk in the Park\n");
            totalTime += 1.0;
            totalCost += 0.0;
        }
        summary.append(String.format("\nTotal Time: %.1f hours\nTotal Cost: $%.1f", totalTime, totalCost));
        planSummaryArea.setText(summary.toString());
    }


    private void onSortSelected(ActionEvent e) {
        List<City> sorted = getCurrentSortStrategy().sort(CityRepository.getInstance().getCities());
        sortedCityListModel.clear();
        for (City city : sorted) {
            sortedCityListModel.addElement(formatCityInfo(city));
        }
    }

    private void updateCityList(CitySortStrategy strategy) {
        List<City> sorted = strategy.sort(CityRepository.getInstance().getCities());
        sortedCityListModel.clear();
        for (City city: sorted) {
            sortedCityListModel.addElement(formatCityInfo(city));
        }
    }

    private CitySortStrategy getCurrentSortStrategy() {
        String selected = (String) sortComboBox.getSelectedItem();
        return switch (selected) {
            case "Sort by Population" -> new SortByPopulation();
            case "Sort by Area" -> new SortByArea();
            default -> new SortByName();
        };
    }

    private String formatCityInfo(City city) {
        return String.format("%s (Pop: %,d, Area: %.1f km², Temp: %.1f°C, %s)",
                city.getName(), city.getPopulation(), city.getArea(), city.getCurrentTemperature(), city.getCurrentWeatherState());
    }
}
