package com.lab.model.service;

import com.lab.model.model.MatchEntity;
import com.lab.model.model.TeamEntity;
import com.lab.model.repository.MatchRepository;
import com.lab.model.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamEntity> getAllTeams() {
        return teamRepository.findAll();
    }
}
