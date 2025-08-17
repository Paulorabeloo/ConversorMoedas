import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConversorMoedas {

    public static void executar() {
        Scanner scanner = new Scanner(System.in);
        String[] moedas = {"ARS", "BOB", "BRL", "CLP", "COP", "USD"};
        String apiKey = "969647495d2a9d950c082ec1";
        String API_URL = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/USD";

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            if (!json.has("conversion_rates")) {
                System.out.println("Erro: resposta da API inválida!");
                return;
            }
            JsonObject rates = json.getAsJsonObject("conversion_rates");

            while (true) {
                System.out.println("\n=== Conversor de Moedas ===");
                for (int i = 0; i < moedas.length; i++) {
                    System.out.printf("%d - %s%n", i + 1, moedas[i]);
                }
                System.out.println("0 - Sair");

                System.out.print("Escolha a moeda de origem: ");
                int origemIndex = scanner.nextInt() - 1;
                if (origemIndex == -1) break;

                System.out.print("Escolha a moeda de destino: ");
                int destinoIndex = scanner.nextInt() - 1;

                System.out.print("Digite o valor a ser convertido: ");
                double valor = scanner.nextDouble();

                double convertido = (valor / rates.get(moedas[origemIndex]).getAsDouble()) *
                        rates.get(moedas[destinoIndex]).getAsDouble();

                System.out.printf("%.2f %s = %.2f %s%n", valor, moedas[origemIndex], convertido, moedas[destinoIndex]);
            }

        } catch (Exception e) {
            System.out.println("Erro ao converter moedas: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
