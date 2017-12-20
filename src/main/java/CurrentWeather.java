import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrentWeather {

    private static final String baseUrl = "api.openweathermap.org/data/2.5/";
    private static final String apiID = "&units=metric&appid=97966daf82a2215001ba573b05bc5e41";
    private String city;
    private JSONObject jsonObject;

    public CurrentWeather() {}

    public CurrentWeather(String city) {
        this.city = city;

        String urlRequest = "weather?q=" + city;
        String urlAllTogether = "http://" + baseUrl + urlRequest + apiID;

        // Siin paneme url-i kokku ja küsime andmed apilt
        try {
            URL url = new URL(urlAllTogether);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

        // Loeme vastuse ja teisendame stringiks
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            br.close();

        // String teisendatud json-iks
            jsonObject = new JSONObject(sb.toString());

        } catch (IOException | JSONException e) {
            System.err.println("Linna ei leitud!");
        }

    }

    public Double getCurrentTemperature() {
        try {
            return jsonObject.getJSONObject("main").getDouble("temp");
        } catch (JSONException | NullPointerException e) {
            // Ei kuva midagi, sest linna mitte leidmise teade on varem välja antud
        }
        return null;
    }

    public String getCurrentCity() {
        try {
            return jsonObject.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String getCoordinates() {
        try {
            JSONObject coord = jsonObject.getJSONObject("coord");
            return "lon: " + coord.getDouble("lon") + ", lat: " + coord.getDouble("lat");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }
}
