import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

//    @Test
//    public void getCoordinatesForEachCity() {
////       assertEquals();
//    }
}