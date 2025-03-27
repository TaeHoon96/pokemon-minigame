package org.example.pokemonminigame.controller;

import jakarta.servlet.http.HttpSession;
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
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class IndexController {
    private final PokeUserMySQLRepository pokeUserMySQLRepository;
    private final Logger logger = Logger.getLogger(IndexController.class.getName());

    public IndexController(PokeUserMySQLRepository pokeUserMySQLRepository) {
        this.pokeUserMySQLRepository = pokeUserMySQLRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/")
    public String login(@ModelAttribute PokeUserDTO pokeUserDTO, HttpSession session, Model model) {
        logger.info("로그인 도달");
        String pokeUserID = pokeUserMySQLRepository.getOnePokeUser(pokeUserDTO.username(), pokeUserDTO.password());
        session.setAttribute("pokeUserID", pokeUserID);
        model.addAttribute("pokeUserID", pokeUserID);
        logger.info("로그인한 계정 %s".formatted(pokeUserID));
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
