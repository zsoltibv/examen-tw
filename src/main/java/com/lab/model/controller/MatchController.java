package com.lab.model.controller;

import com.lab.model.model.MatchEntity;
import com.lab.model.repository.MatchRepository;
import com.lab.model.service.MatchService;
import com.lab.model.service.MenuService;
import com.lab.model.util.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/matches")
public class MatchController {
    MatchService matchService;
    MenuService menuService;

    @Autowired
    public MatchController(MatchService matchService, MenuService menuService) {
        this.matchService = matchService;
        this.menuService = menuService;
    }

    @GetMapping()
    public String open(Model model, Authentication authentication){
        List<MenuItem> menu = menuService.buildMenu(authentication);
        List<MatchEntity> allMatches = matchService.getAllMatches();
        model.addAttribute("menuItems", menu);
        model.addAttribute("allMatches", allMatches);
        return "matches";
    }
}