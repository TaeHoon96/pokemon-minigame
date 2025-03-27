package org.example.pokemonminigame.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pokemonminigame.model.dto.GeminiImageDTO;
import org.example.pokemonminigame.model.dto.GeminiRequestDTO;
import org.springframework.stereotype.Component;

import java.net.http.HttpClient;
import java.util.logging.Logger;

// Scan을 통해서 자동으로 등록된 Bean
@Component // 의존성 주입을 할 때
public class GeminiAPI {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(GeminiAPI.class.getName());

    public GeminiImageDTO generateImage(GeminiRequestDTO dto) {
        return new GeminiImageDTO();
    }
}
