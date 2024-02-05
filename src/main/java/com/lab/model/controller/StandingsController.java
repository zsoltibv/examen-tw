package com.lab.model.controller;

import com.lab.model.service.MenuService;
import com.lab.model.service.StandingsService;
import com.lab.model.util.MenuItem;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/standings")
public class StandingsController {
    private final StandingsService standingsService;
    private final MenuService menuService;

    public StandingsController(StandingsService standingsService, MenuService menuService) {
        this.standingsService = standingsService;
        this.menuService = menuService;
    }

    @GetMapping()
    public String open(Model model, Authentication authentication){
        List<MenuItem> menu = menuService.buildMenu(authentication);
        model.addAttribute("menuItems", menu);
        model.addAttribute("allStandings", standingsService.getAllStandings());
        return "standings";
    }
}
