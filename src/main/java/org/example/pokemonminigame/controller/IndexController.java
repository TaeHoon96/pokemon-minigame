package org.example.pokemonminigame.controller;

import jakarta.servlet.http.HttpSession;
import org.example.pokemonminigame.api.TogetherAPI;
import org.example.pokemonminigame.model.dao.PokeImageMySQLRepository;
import org.example.pokemonminigame.model.dao.PokeUserMySQLRepository;
import org.example.pokemonminigame.model.dto.PokeUser;
import org.example.pokemonminigame.model.dto.PokeUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class IndexController {
    private final PokeUserMySQLRepository pokeUserMySQLRepository;
    private final Logger logger = Logger.getLogger(IndexController.class.getName());
    private final TogetherAPI togetherAPI;
    private final PokeImageMySQLRepository pokeImageMySQLRepository;

    public IndexController(PokeUserMySQLRepository pokeUserMySQLRepository, TogetherAPI togetherAPI, PokeImageMySQLRepository pokeImageMySQLRepository) {
        this.pokeUserMySQLRepository = pokeUserMySQLRepository;
        this.togetherAPI = togetherAPI;
        this.pokeImageMySQLRepository = pokeImageMySQLRepository;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) throws Exception {
        if (session.getAttribute("pokeUserID") != null) {
            String pokeUserID = session.getAttribute("pokeUserID").toString();
            String username = pokeUserMySQLRepository.getUsernameByPokeUserId(pokeUserID);
            model.addAttribute("username", username);
            List<String> images = pokeImageMySQLRepository.readImages(pokeUserID);
            model.addAttribute("images", images);
        }
        return "index";
    }

    @PostMapping("/")
    public String login(@ModelAttribute PokeUserDTO pokeUserDTO, HttpSession session, Model model) throws Exception {
        logger.info("로그인 도달");
        String pokeUserID = pokeUserMySQLRepository.getOnePokeUser(pokeUserDTO.username(), pokeUserDTO.password());
        session.setAttribute("pokeUserID", pokeUserID);
//        model.addAttribute("pokeUserID", pokeUserID);
        logger.info("로그인한 계정 %s".formatted(pokeUserID));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("pokeUserID");
        return "redirect:/";
    }

    @GetMapping("/sign-up")
    public String signUp() {
        return "sign-up";
    }

    @GetMapping("/more")
    public String more(HttpSession session) throws Exception {
        if (session.getAttribute("pokeUserID") == null) {
            throw new RuntimeException("로그인 없이 시도하려고 하셨군요?");
        }
        String pokeUserID = session.getAttribute("pokeUserID").toString();
        String prompt = "Draw a cute pokemon  character that reflects the characteristics of {%s}, and if you can't create one, draw a cute image.".formatted(pokeUserMySQLRepository.getUsernameByPokeUserId(pokeUserID));
        pokeImageMySQLRepository.createPokeImage(togetherAPI.generateImage(prompt), pokeUserID);
        return "redirect:/";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute PokeUserDTO pokeUserDTO, HttpSession session) throws Exception {
        UUID userID = UUID.randomUUID();
        pokeUserMySQLRepository.createPokeUser(new PokeUser(userID, pokeUserDTO.username(), pokeUserDTO.password()));
        String prompt = "Draw a cute pokemon  character that reflects the characteristics of {%s}, and if you can't create one, draw a cute image.".formatted(pokeUserDTO.username());
        pokeImageMySQLRepository.createPokeImage(togetherAPI.generateImage(prompt), userID.toString());
        session.setAttribute("pokeUserID", userID.toString());
        return "redirect:/";
    }
}
