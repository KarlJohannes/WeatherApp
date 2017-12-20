import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.MockHandler;
import org.mockito.mock.MockCreationSettings;
import org.mockito.plugins.MockMaker;

import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HttpUtilityTest {

    CurrentWeather weather1;
    CurrentWeather weather2;
    CurrentWeather currentWeatherTest;

    WeatherForecast forecast1;
    WeatherForecast forecast2;
    WeatherForecast forecastTest;

    @Before
    public void beforeTest() {
        weather1 = new CurrentWeather("Tallinn");
        weather2 = new CurrentWeather("Miami");

        forecast1 = new WeatherForecast("Berlin");
        forecast2 = new WeatherForecast("Paris");
    }

    @Test
    public void testIfResponseCityEqualsRequestCityCurrentWeather() {
        String requestCity = weather1.getCurrentCity();

        currentWeatherTest = new CurrentWeather(requestCity);
        String responseCity = currentWeatherTest.getCurrentCity();

        // expected, actual
        assertEquals(requestCity, responseCity);
    }

    @Test
    public void testIfTemperaturesAreEqualInGivenCity() {
        Double expectedTemp = weather2.getCurrentTemperature();

        currentWeatherTest = new CurrentWeather(weather2.getCurrentCity());
        Double responseTemp = currentWeatherTest.getCurrentTemperature();

        assertEquals(expectedTemp, responseTemp);
    }

    @Test
    public void testIfResponseCityEqualsRequestCityWeatherForecast() {
        String requestCity = forecast1.getCurrentCity();

        forecastTest = new WeatherForecast(requestCity);
        String responseCity = forecastTest.getCurrentCity();

        assertEquals(requestCity, responseCity);
    }

    @Test
    public void testMinimumTemperatureForNextDay() {
        Double expectedMinTempDay1 = forecast1.getMinMaxTemperature(1,"min");

        forecastTest = new WeatherForecast(forecast1.getCurrentCity());
        Double responseMinTempDay1 = forecastTest.getMinMaxTemperature(1,"min");

        assertEquals(expectedMinTempDay1, responseMinTempDay1);
    }

    @Test
    public void testMaximumTemperatureForNextDay() {
        Double expectedMaxTempDay1 = forecast2.getMinMaxTemperature(1,"max");

        forecastTest = new WeatherForecast(forecast2.getCurrentCity());
        Double responseMaxTempDay1 = forecastTest.getMinMaxTemperature(1,"max");

        assertEquals(expectedMaxTempDay1, responseMaxTempDay1);
    }

    @Test
    public void testCurrentWeatherWithoutApiCall() {

        CurrentWeather cw = new CurrentWeather();

        cw.setCity("Tallinn");
        try {
            cw.setJsonObject(new JSONObject("{\"coord\":{\"lon\":43.21,\"lat\":12.34},\"weather\":[{\"id\":300,\"main\":\"Drizzle\",\"description\":\"light intensity drizzle\",\"icon\":\"09d\"}],\"base\":\"stations\",\"main\":{\"temp\":13,\"pressure\":1012,\"humidity\":81,\"temp_min\":279.15,\"temp_max\":281.15},\"visibility\":10000,\"wind\":{\"speed\":4.1,\"deg\":80},\"clouds\":{\"all\":90},\"dt\":1485789600,\"sys\":{\"type\":1,\"id\":5091,\"message\":0.0103,\"country\":\"GB\",\"sunrise\":1485762037,\"sunset\":1485794875},\"id\":2643743,\"name\":\"Tallinn\",\"cod\":200}"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assertEquals("Tallinn", cw.getCurrentCity());
        assertEquals((Double) 13.0, cw.getCurrentTemperature());
        assertEquals("lon: 43.21, lat: 12.34", cw.getCoordinates());
    }

}