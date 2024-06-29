package Experimento;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class WeatherService {
    private static final String API_KEY = "4860277b2a035d873dad0991c78874e7";  // Substitua com sua chave real da API

    // Método para fazer a requisição à API e retornar dados de temperatura
    public static Map<String, List<Double>> fazerRequisicao(CityData cidade) {
        String cityNameEncoded = URLEncoder.encode(cidade.nome, StandardCharsets.UTF_8);
        String link = String.format(
            "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric&lang=pt_br",
            cityNameEncoded, API_KEY
        );
        Map<String, List<Double>> result = new HashMap<>();
        try {
            URL url = new URL(link);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            conexao.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            StringBuilder content = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            JSONObject json = new JSONObject(content.toString());
            double temp = json.getJSONObject("main").getDouble("temp");
            double minTemp = json.getJSONObject("main").getDouble("temp_min");
            double maxTemp = json.getJSONObject("main").getDouble("temp_max");

            result.put(cidade.nome, List.of(minTemp, maxTemp, temp));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // Método para processar os dados obtidos e imprimir no console
    public static void processarDados(String cidade, List<Double> temperaturas) {
        double min = temperaturas.get(0);
        double max = temperaturas.get(1);
        double media = temperaturas.get(2);
        System.out.println("Cidade: " + cidade + " - Min: " + min + ", Max: " + max + ", Média: " + media + "°C");
    }
}