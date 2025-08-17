import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class TesteExchangeRate {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        String apiKey = "969647495d2a9d950c082ec1";
        String url = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Resposta da API:");
        System.out.println(response.body());
    }
}
