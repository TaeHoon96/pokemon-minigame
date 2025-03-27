package org.example.pokemonminigame.model.dto;

import java.util.List;

/*
{
    "contents": [
        {
            "role": "user",
            "parts": [
                {
                    "text": "거북이 그려줘"
                }
            ]
        }
    ],
      "generationConfig": {
        "responseModalities": [
          "image",
          "text"
        ]
  }
}
*/
public record GeminiRequestDTO(List<Content> contents, GenerationConfig generationConfig) {
    public record Content(String role, List<Part> parts) {}
    public record Part(String text) {}
    public record GenerationConfig(List<String> responseModalities) {}
}
