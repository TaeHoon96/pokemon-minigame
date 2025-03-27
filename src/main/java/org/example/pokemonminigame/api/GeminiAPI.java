package org.example.pokemonminigame.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.pokemonminigame.model.dto.GeminiImageDTO;
import org.example.pokemonminigame.model.dto.GeminiRequestDTO;
import org.example.pokemonminigame.model.dto.GeminiResponseDTO;
import org.example.pokemonminigame.util.DotenvMixin;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

// Scan을 통해서 자동으로 등록된 Bean
@Component // 의존성 주입을 할 때
public class GeminiAPI implements DotenvMixin {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Logger logger = Logger.getLogger(GeminiAPI.class.getName());

    public GeminiImageDTO generateImage(GeminiRequestDTO dto) throws Exception {
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash-exp-image-generation:generateContent?key=%s".formatted(dotenv.get("GEMINI_API_KEY"));
        // {
        //  "contents": [
        //    {
        //      "role": "user",
        //      "parts": [
        //        {
        //          "text": "거북이 그려줘"
        //        }
        //      ]
        //    }
        //  ]
        // }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                .build();
        HttpResponse<String> response = httpClient.send(
            request, HttpResponse.BodyHandlers.ofString()
        );
//        logger.info(response.body());
        String data = objectMapper.readValue(response.body(), GeminiResponseDTO.class).candidates().get(0).content().parts().get(0).inlineData().data();
//        logger.info(data);
        logger.info(String.valueOf(data.length()));
        return new GeminiImageDTO(data);
    }
}
