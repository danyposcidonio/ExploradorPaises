
package com.ExploradorPaises.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.ExploradorPaises.model.Country;
import com.ExploradorPaises.model.CountryResponse;

public class CountryService {

    private static final String API_URL =
            "https://api.restcountries.com/countries/v5";

    // Coloque sua nova chave da API aqui.
    // Não compartilhe essa chave publicamente.
    private static final String API_KEY = "rc_live_25dd938514f14fb995c0afc5bbd0c094";

    private final HttpClient client = HttpClient.newHttpClient();

    private final Gson gson = new Gson();

    // Consulta GET à API
    public String listarPaises()
            throws IOException, InterruptedException {

        String url = API_URL
                + "?limit=100"
                + "&response_fields=names.common,"
                + "codes.alpha_2,"
                + "flag.emoji,"
                + "flag.url_png,"
                + "region,"
                + "subregion,"
                + "capitals.name,"
                + "population,"
                + "area.kilometers,"
                + "currencies.code,"
                + "currencies.name,"
                + "languages.name";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + API_KEY)
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
        );

        if (response.statusCode() == 200) {

            System.out.println("Consulta realizada com sucesso!");

            return response.body();

        } else {

            throw new IOException(
                    "Erro HTTP " + response.statusCode()
                    + ": " + response.body()
            );
        }
    }

    // Converte o JSON em uma lista de países
    public List<Country> obterPaises()
            throws IOException, InterruptedException {

        String json = listarPaises();

        CountryResponse resposta = gson.fromJson(
                json,
                CountryResponse.class
        );

        if (resposta == null) {
            throw new IOException("Resposta inválida da API.");
        }

        return resposta.getCountries();
    }
}
