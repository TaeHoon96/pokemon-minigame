package org.example.pokemonminigame.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TogetherAPIResponseDTO (List<Data> data) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Data(String url) {}
}
