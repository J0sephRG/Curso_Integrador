package apiclientes;

import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClienteServiceImpl implements ApiClienteService {

    private static final String TOKEN = "apis-token-16670.3PCrUQlH2emMRFew1P40pUR7cInJNVpB"; // <-- reemplaza con tu token real

    @Override
    public String obtenerNombrePorDni(String dni) {
        String url = "https://api.apis.net.pe/v2/reniec/dni?numero=" + dni;
        return consultarApiReniec(url);
    }

    @Override
    public String obtenerRazonSocialPorRuc(String ruc) {
        String url = "https://api.apis.net.pe/v2/sunat/ruc?numero=" + ruc;
        return consultarApiSunat(url);
    }

    private String consultarApiReniec(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error en respuesta HTTP (RENIEC): " + response.statusCode());
            }

            String body = response.body();

            if (!body.trim().startsWith("{")) {
                throw new RuntimeException("La respuesta no es un JSON válido. Cuerpo: " + body);
            }

            JSONObject json = new JSONObject(body);
            return json.getString("nombres") + " " +
                   json.getString("apellidoPaterno") + " " +
                   json.getString("apellidoMaterno");

        } catch (Exception e) {
            throw new RuntimeException("Error consultando DNI en RENIEC", e);
        }
    }

    private String consultarApiSunat(String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error en respuesta HTTP (SUNAT): " + response.statusCode());
            }

            String body = response.body();

            if (!body.trim().startsWith("{")) {
                throw new RuntimeException("La respuesta no es un JSON válido. Cuerpo: " + body);
            }

            JSONObject json = new JSONObject(body);
            return json.getString("razonSocial");

        } catch (Exception e) {
            throw new RuntimeException("Error consultando RUC en SUNAT", e);
        }
    }
}
