package com.lab.model.service;

import com.lab.model.model.MatchEntity;
import com.lab.model.model.TeamEntity;
import com.lab.model.repository.MatchRepository;
import com.lab.model.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public List<MatchEntity> getAllMatches() {
        return matchRepository.findAll();
    }

    public void addMatch(Long team1Id, Long team2Id, Long winnerId, Boolean isDraw) {
        // Fetch teams and winner from the repository
        TeamEntity team1 = teamRepository.findById(team1Id).orElseThrow();
        TeamEntity team2 = teamRepository.findById(team2Id).orElseThrow();
        TeamEntity winner = teamRepository.findById(winnerId).orElse(null);

        // Create a new match entity
        MatchEntity newMatch = new MatchEntity();
        newMatch.setTeam1(team1);
        newMatch.setTeam2(team2);
        if (isDraw) {
            newMatch.setScoreTeam1(1);
            newMatch.setScoreTeam2(1);
        } else if (winnerId.equals(team1Id)) {
            newMatch.setScoreTeam1(3);
            newMatch.setScoreTeam2(0);
        } else {
            newMatch.setScoreTeam1(0);
            newMatch.setScoreTeam2(3);
        }
        // Save the new match
        matchRepository.save(newMatch);
    }
}
