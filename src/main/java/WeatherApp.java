import org.json.JSONException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class WeatherApp {

    public static void main(String[] args) {

        WeatherApp weatherApp = new WeatherApp();

        while (true) {

            System.out.println("Vali tegevus (L - sisesta linn käsitsi, F - loe linn failist, E - exit): ");
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            if (input.toLowerCase().equals("l")) {
                System.out.println("Sisesta linn: ");
                Scanner scanner2 = new Scanner(System.in);
                String input2 = scanner2.nextLine();

                CurrentWeather ilm = new CurrentWeather(input2);
                System.out.println(ilm.getCurrentCity() + ": " + ilm.getCurrentTemperature());

            } else if (input.toLowerCase().equals("f")) {
                System.out.println("Sisesta input fail: ");
                Scanner scanner3 = new Scanner(System.in);
                String inputFail = scanner3.nextLine();

                weatherApp.readCityAndWriteWeatherToFile(inputFail);

            } else if (input.toLowerCase().equals("e")) {
                break;
            }

        }

    }

    public void readCityAndWriteWeatherToFile(String inputFile) {

        try {
            // Loeme rea kaupa ja lisame cities listi linnad - input.txt
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile + ".txt"));

            ArrayList<String> cities = new ArrayList<String>();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                cities.add(line);
            }

            // Igale linnale oma fail ja ilmateade
            for (String city : cities) {

                CurrentWeather ilm2 = new CurrentWeather(city);
                WeatherForecast weatherForecast2 = new WeatherForecast(city);

                // Kirjutame faili - {linnanimi}.txt
                Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(city + ".txt"), "utf-8"));
                writer.write(city + "\n");
                writer.write("Koordinaadid: " + ilm2.getCoordinates() + "\n");
                writer.write("Hetkeilm: \n" + ilm2.getCurrentCity() + ": " + ilm2.getCurrentTemperature() + "\n");
                writer.write("Ilmaennustus: \n" +
                        "Päev 1: min = " + weatherForecast2.getMinMaxTemperature(1, "min") +
                        " ja max = " + weatherForecast2.getMinMaxTemperature(1, "max") + "\n" +
                        "Päev 2: min = " + weatherForecast2.getMinMaxTemperature(2, "min") +
                        " ja max = " + weatherForecast2.getMinMaxTemperature(2, "max") + "\n" +
                        "Päev 3: min = " + weatherForecast2.getMinMaxTemperature(3, "min") +
                        " ja max = " + weatherForecast2.getMinMaxTemperature(3, "max")
                );
                writer.close();
            }

        } catch (FileNotFoundException e) {
            System.out.println("Faili ei leitud.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
