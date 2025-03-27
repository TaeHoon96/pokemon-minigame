package org.example.pokemonminigame.controller;

import org.example.pokemonminigame.model.dao.PokeUserMySQLRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
    private final PokeUserMySQLRepository pokeUserMySQLRepository;

    public IndexController(PokeUserMySQLRepository pokeUserMySQLRepository) {
        this.pokeUserMySQLRepository = pokeUserMySQLRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }
}
