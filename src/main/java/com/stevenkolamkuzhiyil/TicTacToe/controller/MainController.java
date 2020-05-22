package com.stevenkolamkuzhiyil.TicTacToe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping(path = {"", "index"})
    public String index() {
        return "index";
    }

    @GetMapping(path = {"game", "game/index"})
    public String game() {
        return "game/play";
    }

}
