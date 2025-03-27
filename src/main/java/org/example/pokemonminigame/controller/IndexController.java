package org.example.pokemonminigame.controller;

import jakarta.servlet.http.HttpSession;
import org.example.pokemonminigame.api.GeminiAPI;
import org.example.pokemonminigame.model.dao.PokeUserMySQLRepository;
import org.example.pokemonminigame.model.dto.GeminiRequestDTO;
import org.example.pokemonminigame.model.dto.PokeUser;
import org.example.pokemonminigame.model.dto.PokeUserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Controller
@RequestMapping("/")
public class IndexController {
    private final PokeUserMySQLRepository pokeUserMySQLRepository;
    private final Logger logger = Logger.getLogger(IndexController.class.getName());
    private final GeminiAPI geminiAPI;

    public IndexController(PokeUserMySQLRepository pokeUserMySQLRepository, GeminiAPI geminiAPI) {
        this.pokeUserMySQLRepository = pokeUserMySQLRepository;
        this.geminiAPI = geminiAPI;
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
    public String signUp(@ModelAttribute PokeUserDTO pokeUserDTO) throws Exception {
        pokeUserMySQLRepository.createPokeUser(new PokeUser(UUID.randomUUID(), pokeUserDTO.username(), pokeUserDTO.password()));
        String prompt = "%s와 어울리는 포켓몬 이미지를 그려줘.".formatted(pokeUserDTO.username());
        List<GeminiRequestDTO.Content> contents = List.of(new GeminiRequestDTO.Content("user", List.of(new GeminiRequestDTO.Part(prompt))));
        GeminiRequestDTO.GenerationConfig generationConfig = new GeminiRequestDTO.GenerationConfig(List.of("image", "text"));
        GeminiRequestDTO geminiRequestDTO = new GeminiRequestDTO(contents, generationConfig);
        geminiAPI.generateImage(geminiRequestDTO);
        return "redirect:/";
    }

}
