import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class WeatherForecast {

    private static final String baseUrl = "api.openweathermap.org/data/2.5/";
    private static final String apiID = "&units=metric&appid=97966daf82a2215001ba573b05bc5e41";
    private String city;
    private JSONObject jsonObject;

    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private Date currentDate = new Date();

    public WeatherForecast(String city) {
        this.city = city;

        String urlRequest = "forecast?q=" + city;
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
            e.printStackTrace();
        }

    }

    public String getCurrentCity() {
        try {
            return jsonObject.getJSONObject("city").getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public Double getMinMaxTemperature(int day, String minOrMax) {

        // Kontroll, et oleks valitud 1-3 päevane vahemik
        if (day < 1 || day > 3) {
            return null;
        }

        // Kontroll et sisend oleks min või max
        if (!minOrMax.toLowerCase().equals("min") && !minOrMax.toLowerCase().equals("max")) {
            return null;
        }

        LocalDateTime d = LocalDateTime.from(currentDate.toInstant().atZone(ZoneId.of("UTC+03:00"))).plusDays(1);
        String date = d.format(dateFormat);

        // Käime ilmaennustuste listi läbi for tsükliga ja kui ennustuse kuupäev
        // on võrdne meie valitud kuupäevaga, lisatakse see uute listi (forecastOnSelectedDate)
        JSONArray forecastOnSelectedDate = new JSONArray();
        try {
            JSONArray forecast = jsonObject.getJSONArray("list");

            for (int i = 0; i < forecast.length(); i++) {
                if (forecast.getJSONObject(i).getString("dt_txt").substring(0,10).equals(date)) {
                    forecastOnSelectedDate.put(forecast.getJSONObject(i));
                };
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Otsime max temperatuurid välja
        Double return_temp = null;
        try {

            for (int i = 0; i < forecastOnSelectedDate.length(); i++) {
                Double temp = forecastOnSelectedDate.getJSONObject(i).getJSONObject("main").getDouble("temp_max");
                if (return_temp == null) {
                    return_temp = temp;
                }

                if (temp > return_temp && minOrMax.toLowerCase().equals("max")) {
                    return_temp = temp;
                }

                if (temp < return_temp && minOrMax.toLowerCase().equals("min")) {
                    return_temp = temp;
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return return_temp;
    }

}
