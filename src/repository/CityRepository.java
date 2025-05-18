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
            FileReader reader;
            String json;
            // .jar çalışıyorsa aynı dizindeki cities.json'u dene
//            if (new File("cities.json").exists()) {
//                reader = new FileReader("cities.json");
//            } else {
//                // IntelliJ için src içindeki dosyayı dene
//                reader = new FileReader("src/cities.json");
//            }
            InputStream inputStream = MainUI.class.getResourceAsStream("/cities.json");
             try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while((line=br.readLine()) !=null) {
                        sb.append(line);
                    }
                    json = sb.toString();
             }
            JSONArray cityArray;
            cityArray = new JSONArray(json);
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
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
