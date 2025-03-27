package org.example.pokemonminigame.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public interface HttpClientMixin {
    HttpClient httpClient = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
}
