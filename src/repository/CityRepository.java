package repository;

import model.City;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import ui.MainUI;

public class CityRepository {
    private static CityRepository instance;
    private List<City> cities;

    private CityRepository() {
        cities = new ArrayList<>();
        loadCitiesFromJson();
    }

    public static CityRepository getInstance() {
        if (instance == null) {
            instance = new CityRepository();
        }
        return instance;
    }

    public List<City> getCities() {
        return cities;
    }

    private void loadCitiesFromJson() {
        JSONParser parser = new JSONParser();
        try {
            String json;

            InputStream inputStream = MainUI.class.getResourceAsStream("/resources/cities.json");
            if (inputStream == null) {
                throw new FileNotFoundException("cities.json not found in resources.");
            }

            try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                json = sb.toString();
            }

            // ✅ Use JSONParser to parse string into JSONArray
            JSONArray cityArray = (JSONArray) parser.parse(json);

            for (Object obj : cityArray) {
                JSONObject cityJson = (JSONObject) obj;
                String name = (String) cityJson.get("name");
                long population = (long) cityJson.get("population");
                double area = ((Number) cityJson.get("area")).doubleValue();
                double temperature = ((Number) cityJson.get("currentTemperature")).doubleValue();
                String weather = (String) cityJson.get("currentWeatherState");

                City city = new City(name, (int) population, area, temperature, weather);
                cities.add(city);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
