import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HolidayAPI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("入力された年の祝日を取得します。整数で入力してください。:");
        String year = scanner.next();

        try {
            String apiUrl = "https://holidays-jp.github.io/api/v1/" + year + "/date.json";
            URL url = new URL(apiUrl);
            URLConnection connection = url.openConnection();

            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println(response.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] getNationalHoliday(int yyyy) {
        ObjectMapper objectMapper = new ObjectMapper();
        return null;
    }

    public static String[] getResponseBodyTo(String url) {
        return null;
    }
}
