import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    public static String getWeather(String message, Model model) throws IOException {

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=900c3a80721771d783a4550a7563f743");

        Scanner scanner = new Scanner((InputStream) url.getContent());
        StringBuilder result = new StringBuilder();
        while (scanner.hasNext()) {
            result.append(scanner.nextLine());
        }

        JSONObject jsObject = new JSONObject(result.toString());
        model.setName(jsObject.getString("name"));

        JSONObject mainObject = jsObject.getJSONObject("main");
        model.setTemp(mainObject.getDouble("temp"));
        model.setHumidity(mainObject.getDouble("humidity"));

        JSONArray jsonArray = jsObject.getJSONArray("weather");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject weatherObject = jsonArray.getJSONObject(i);
            model.setIcon((String) weatherObject.get("icon"));
            model.setMain(weatherObject.getString("main"));
        }

        return "City: " + model.getName() +
                "\nTemp: " + model.getTemp() + " C" +
                "\nHumidity: " + model.getHumidity() + " %" +
                "\nMain: " + model.getMain() +
                "\nhttp://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }
}
