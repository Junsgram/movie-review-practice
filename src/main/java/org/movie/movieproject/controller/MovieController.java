package org.movie.movieproject.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.movie.movieproject.dto.MovieDTO;
import org.movie.movieproject.dto.PageRequestDTO;
import org.movie.movieproject.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.lang.management.MemoryNotificationInfo;

@Controller
@RequestMapping("/movie")
@Log4j2
@RequiredArgsConstructor
public class MovieController {
    // Field
    private final MovieService movieService;

    // Method
    @GetMapping("/register")
    public void register() {

    }
    @PostMapping("/register")
    public String register(MovieDTO movieDTO, RedirectAttributes redirectAttributes) {
        log.info("movieDTO :" + movieDTO);
        Long mno = movieService.register(movieDTO);
        redirectAttributes.addFlashAttribute("msg", mno);
        return "redirect:/movie/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result" , movieService.getList(pageRequestDTO));
    }

    @GetMapping({"/read", "/modify"})
    public void read(@RequestParam(value = "mno")Long mno,
                     @ModelAttribute("pageRequestDTO") PageRequestDTO pageRequestDTO, Model model) {
        MovieDTO movieDTO = movieService.getMovie(mno);
        model.addAttribute("dto", movieDTO);

    }

}
