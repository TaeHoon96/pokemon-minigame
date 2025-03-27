package org.example.pokemonminigame.model.dto;

import java.util.UUID;

public record PokeUser(UUID id, String username, String password) { }
