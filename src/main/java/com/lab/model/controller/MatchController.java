package com.lab.model.controller;

import com.lab.model.model.MatchEntity;
import com.lab.model.model.TeamEntity;
import com.lab.model.service.MatchService;
import com.lab.model.service.MenuService;
import com.lab.model.service.TeamService;
import com.lab.model.util.MenuItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/matches")
public class MatchController {
    MatchService matchService;
    MenuService menuService;
    TeamService teamService;

    @Autowired
    public MatchController(MatchService matchService, MenuService menuService, TeamService teamService) {
        this.matchService = matchService;
        this.menuService = menuService;
        this.teamService = teamService;
    }

    @GetMapping()
    public String open(Model model, Authentication authentication){
        List<MenuItem> menu = menuService.buildMenu(authentication);
        List<MatchEntity> allMatches = matchService.getAllMatches();
        List<TeamEntity> allTeams = teamService.getAllTeams();

        model.addAttribute("menuItems", menu);
        model.addAttribute("allMatches", allMatches);
        model.addAttribute("allTeams", allTeams);
        return "matches";
    }

    @PostMapping("/add")
    public String addMatch(
            @RequestParam Long team1,
            @RequestParam Long team2,
            @RequestParam(required = false) Long winner,
            @RequestParam(required = false) boolean isDraw) {

        // Save the new match
        matchService.addMatch(team1, team2, winner, isDraw);

        return "redirect:/matches";
    }

    private void validateMatchParameters(Long team1, Long team2, Long winner, boolean isDraw, BindingResult bindingResult) {
        // Check for null teams
        if (team1 == null || team2 == null) {
            bindingResult.addError(new FieldError("match", "team1", "Team IDs cannot be null"));
            bindingResult.addError(new FieldError("match", "team2", "Team IDs cannot be null"));
        }

        // Check that teams are not the same
        if (team1 != null && team2 != null && team1.equals(team2)) {
            bindingResult.addError(new FieldError("match", "team2", "Teams cannot be the same"));
        }
    }
}
