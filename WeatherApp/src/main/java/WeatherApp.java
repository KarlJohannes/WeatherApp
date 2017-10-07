public class WeatherApp {

    public static void main(String[] args) {

        CurrentWeather ilm = new CurrentWeather("Tallinn");
        System.out.println(ilm.getCurrentTemperature());
        System.out.println(ilm.getCurrentCity());

        WeatherForecast weatherForecast = new WeatherForecast("Berlin");

    }

}
