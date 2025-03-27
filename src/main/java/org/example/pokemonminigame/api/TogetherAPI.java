package org.example.pokemonminigame.api;

import org.example.pokemonminigame.model.dto.TogetherAPIRequestDTO;
import org.example.pokemonminigame.model.dto.TogetherAPIResponseDTO;
import org.example.pokemonminigame.util.DotenvMixin;
import org.example.pokemonminigame.util.HttpClientMixin;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

// Scan을 통해서 자동으로 등록된 Bean
@Component // 의존성 주입을 할 때
public class TogetherAPI implements DotenvMixin, HttpClientMixin {
    private final Logger logger = Logger.getLogger(TogetherAPI.class.getName());

    private final String baseUrl = "https://api.together.xyz/v1";
    private final String apiKey = dotenv.get("TOGETHER_API_KEY");



    public String generateImage(String prompt) throws Exception {
        TogetherAPIRequestDTO dto = new TogetherAPIRequestDTO("black-forest-labs/FLUX.1-schnell-Free", prompt, 1024, 768, 4, 1);
        String url = "%s/%s".formatted(baseUrl, "images/generations");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer %s".formatted(apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(dto)))
                .build();
        HttpResponse<String> response = httpClient.send(
            request, HttpResponse.BodyHandlers.ofString()
        );
        String responseBody = response.body();
        logger.info(responseBody);
        String imageUrl = objectMapper.readValue(responseBody, TogetherAPIResponseDTO.class).data().get(0).url();
        logger.info(imageUrl);
        return imageUrl;
    }
}
