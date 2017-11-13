import java.io.*;
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

                System.out.println("Sisesta output fail: ");
                Scanner scanner4 = new Scanner(System.in);
                String outputFail = scanner4.nextLine();

                weatherApp.readCityAndWriteWeatherToFile(inputFail, outputFail);

            } else if (input.toLowerCase().equals("e")) {
                break;
            }

        }

    }

    public void readCityAndWriteWeatherToFile(String inputFile, String outputFile) {

        try {
            // Loeme rea - input.txt
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile + ".txt"));
            String line = bufferedReader.readLine();
            System.out.println(inputFile + ".txt " + line);
            // Küsime info selle loetud rea kohta
            CurrentWeather ilm2 = new CurrentWeather(line);
            WeatherForecast weatherForecast2 = new WeatherForecast(line);

            // Kirjutame faili - output.txt
            Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile + ".txt"), "utf-8"));
            writer.write("Hetkeilm: \n" + ilm2.getCurrentCity() + ": " + ilm2.getCurrentTemperature() + "\n");
            writer.write("Ilmaennustus: \n" +
                    "Päev 1: min = " + weatherForecast2.getMinMaxTemperature(1,"min") +
                    " ja max = " + weatherForecast2.getMinMaxTemperature(1,"max") + "\n" +
                    "Päev 2: min = " + weatherForecast2.getMinMaxTemperature(2,"min") +
                    " ja max = " + weatherForecast2.getMinMaxTemperature(2,"max") + "\n" +
                    "Päev 3: min = " + weatherForecast2.getMinMaxTemperature(3,"min") +
                    " ja max = " + weatherForecast2.getMinMaxTemperature(3,"max")
            );
            writer.close();

        } catch (FileNotFoundException e) {
            System.out.println("Faili ei leitud.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
