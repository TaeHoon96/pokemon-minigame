package org.example.pokemonminigame.controller;

import org.example.pokemonminigame.model.dao.PokeUserMySQLRepository;
import org.example.pokemonminigame.model.dto.PokeUser;
import org.example.pokemonminigame.model.dto.PokeUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

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

    @GetMapping("/sign-up")
    public String signUp() {
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute PokeUserDTO pokeUserDTO) {
        pokeUserMySQLRepository.createPokeUser(new PokeUser(
                UUID.randomUUID(),
                pokeUserDTO.username(),
                pokeUserDTO.password()
        ));
        return "redirect:/";
    }
}
